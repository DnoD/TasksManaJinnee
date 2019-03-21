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
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.databinding.FragmentTasksBinding
import com.dnod.tasksmanajinnee.ui.Conductor
import com.dnod.tasksmanajinnee.ui.ScreenBuilderFactory
import com.dnod.tasksmanajinnee.ui.base.BaseFragment

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

    @Inject
    lateinit var conductor: Conductor<Conductor.ScreenBuilder<BaseFragment>>

    @Inject
    lateinit var screenBuilderFactory: ScreenBuilderFactory<BaseFragment>

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
                showMessage(R.string.message_under_construction)
            })
            nonOptionalViewModel.createTaskAction.observe(this@TasksFragment, Observer {
                showMessage(R.string.message_under_construction)
            })
            setupChannelsList()
            nonOptionalViewModel.start()
        }
        return viewDataBinding.root
    }

    override fun onItemClick(task: Task) {
        showMessage(R.string.message_under_construction)
    }

    override fun getScreenTag(): String {
        return TAG
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
