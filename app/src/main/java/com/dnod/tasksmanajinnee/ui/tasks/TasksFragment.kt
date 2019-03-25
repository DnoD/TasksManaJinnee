package com.dnod.tasksmanajinnee.ui.tasks

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListPopupWindow
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.data.source.TasksDataSource
import com.dnod.tasksmanajinnee.databinding.FragmentTasksBinding
import com.dnod.tasksmanajinnee.manager.SortingManager
import com.dnod.tasksmanajinnee.sorting.SortingProvider
import com.dnod.tasksmanajinnee.ui.Conductor
import com.dnod.tasksmanajinnee.ui.ScreenBuilderFactory
import com.dnod.tasksmanajinnee.ui.base.BaseFragment
import com.dnod.tasksmanajinnee.ui.task.TaskFragment
import com.dnod.tasksmanajinnee.ui.taskdetails.TaskDetailsFragment

import javax.inject.Inject

class TasksFragment : BaseFragment(), TasksAdapter.Listener {

    companion object {
        private val TAG = TasksFragment::class.java.simpleName

        fun createInstance(): BaseFragment {
            val fragment = TasksFragment()
            val arguments = Bundle()
            fragment.arguments = arguments
            return fragment
        }
    }

    private lateinit var viewDataBinding: FragmentTasksBinding
    private lateinit var sortPopupWindow: ListPopupWindow
    private lateinit var sortPopupAdapter: SortPopupAdapter

    @Inject
    lateinit var conductor: Conductor<Conductor.ScreenBuilder<BaseFragment>>

    @Inject
    lateinit var screenBuilderFactory: ScreenBuilderFactory<BaseFragment>

    @Inject
    lateinit var tasksDataSource: TasksDataSource

    @Inject
    lateinit var sortingProvider: SortingProvider

    @Inject
    lateinit var sortingManager: SortingManager

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tasks, container, false)
        viewDataBinding.apply {
            viewModel = ViewModelProviders.of(this@TasksFragment).get(TasksViewModel::class.java)
            val nonOptionalViewModel = viewModel?.let { it } ?: return@apply
            nonOptionalViewModel.alertAction.observe(this@TasksFragment, Observer {
                showMessage(R.string.message_under_construction)
            })
            nonOptionalViewModel.sortAction.observe(this@TasksFragment, Observer {
                if (sortPopupWindow.isShowing) {
                    sortPopupWindow.dismiss()
                } else {
                    showSortPopup()
                }
            })
            nonOptionalViewModel.createTaskAction.observe(this@TasksFragment, Observer {
                conductor.goTo(screenBuilderFactory.create(TaskFragment.createInstance()))
            })
            setupSortPopup()
            setupChannelsList()
            nonOptionalViewModel.start(tasksDataSource)
        }
        return viewDataBinding.root
    }

    override fun onItemClick(task: Task) {
        conductor.goTo(screenBuilderFactory.create(TaskDetailsFragment.createInstance(task.id)))
    }

    override val rootView: View
        get() = viewDataBinding.tasksRoot

    override fun getScreenTag(): String {
        return TAG
    }

    override fun onPause() {
        super.onPause()
        if (sortPopupWindow.isShowing) {
            sortPopupWindow.dismiss()
        }
    }

    private fun showSortPopup() {
        sortPopupWindow.show()
    }

    private fun setupSortPopup() {
        sortPopupWindow = ListPopupWindow(context)
        sortPopupWindow.anchorView = viewDataBinding.toolbar.rightIcon
        sortPopupWindow.setContentWidth(resources.getDimension(R.dimen.sort_popup_width).toInt())

        sortPopupAdapter = SortPopupAdapter(context, sortingProvider.getCurrentSortModel(), sortingProvider.getAvailableSortValues())
        sortPopupAdapter.setDropDownViewResource(R.layout.item_sort_menu)
        sortPopupWindow.setAdapter(sortPopupAdapter)
        sortPopupWindow.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, id: Long ->
            sortPopupAdapter.setSelectedSortModel(sortingManager.applySortModel(sortingProvider.getAvailableSortValues()[position]))
            viewDataBinding.viewModel?.onRefresh()
            sortPopupWindow.dismiss()
        }
    }

    private fun setupChannelsList() {
        context?.let { it ->
            viewDataBinding.tasks.layoutManager = LinearLayoutManager(it)
            val dividerDecoration = DividerItemDecoration(it, DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(it, R.drawable.shape_list_divider)?.let {
                dividerDecoration.setDrawable(it)
            }
            viewDataBinding.tasks.addItemDecoration(dividerDecoration)
            viewDataBinding.tasks.adapter = TasksAdapter(it, this@TasksFragment)
        }
    }
}
