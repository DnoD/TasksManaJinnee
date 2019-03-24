package com.dnod.tasksmanajinnee.ui.taskdetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.source.TasksDataSource
import com.dnod.tasksmanajinnee.databinding.FragmentTaskDetailsBinding
import com.dnod.tasksmanajinnee.ui.Conductor
import com.dnod.tasksmanajinnee.ui.base.BaseFragment

import javax.inject.Inject

class TaskDetailsFragment : BaseFragment() {

    companion object {
        private val TAG = TaskDetailsFragment::class.java.simpleName
        private val PROVIDED_TASK_ID = "provided_task_id"

        fun createInstance(taskId: String): BaseFragment {
            val fragment = TaskDetailsFragment()
            val arguments = Bundle()
            arguments.putString(PROVIDED_TASK_ID, taskId)
            fragment.arguments = arguments
            return fragment
        }
    }

    private lateinit var viewDataBinding: FragmentTaskDetailsBinding

    @Inject
    lateinit var conductor: Conductor<Conductor.ScreenBuilder<BaseFragment>>

    @Inject
    lateinit var tasksDataSource: TasksDataSource

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_details, container, false)
        viewDataBinding.apply {
            viewModel = ViewModelProviders.of(this@TaskDetailsFragment).get(TaskDetailsViewModel::class.java)
            val nonOptionalViewModel = viewModel?.let { it } ?: return@apply
            nonOptionalViewModel.backAction.observe(this@TaskDetailsFragment, Observer {
                conductor.goBack()
            })
            nonOptionalViewModel.editAction.observe(this@TaskDetailsFragment, Observer {
                showMessage(R.string.message_under_construction)
            })
            nonOptionalViewModel.deleteAction.observe(this@TaskDetailsFragment, Observer {
                showMessage(R.string.message_under_construction)
            })
            nonOptionalViewModel.start(arguments?.getString(PROVIDED_TASK_ID) ?: "", tasksDataSource)
        }
        return viewDataBinding.root
    }

    override val rootView: View
        get() = viewDataBinding.detailsRoot

    override fun getScreenTag(): String {
        return TAG
    }
}