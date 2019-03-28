package com.dnod.tasksmanajinnee.ui.taskdetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.data.source.TasksDataSource
import com.dnod.tasksmanajinnee.databinding.FragmentTaskDetailsBinding
import com.dnod.tasksmanajinnee.manager.ReminderManager
import com.dnod.tasksmanajinnee.ui.Conductor
import com.dnod.tasksmanajinnee.ui.ScreenBuilderFactory
import com.dnod.tasksmanajinnee.ui.base.BaseFragment
import com.dnod.tasksmanajinnee.ui.task.TaskFragment

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

    private val errorObserver = Observer<String> { error ->
        error?.let {
            showMessage(it)
        }
    }

    private lateinit var viewDataBinding: FragmentTaskDetailsBinding

    @Inject
    lateinit var conductor: Conductor<Conductor.ScreenBuilder<BaseFragment>>

    @Inject
    lateinit var screenBuilderFactory: ScreenBuilderFactory<BaseFragment>

    @Inject
    lateinit var tasksDataSource: TasksDataSource

    @Inject
    lateinit var reminderManager: ReminderManager

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
                conductor.goTo(screenBuilderFactory.create(TaskFragment.createInstance(arguments?.getString(PROVIDED_TASK_ID))))
            })
            nonOptionalViewModel.deleteAction.observe(this@TaskDetailsFragment, Observer { task ->
                task?.let {
                    showDeleteConfirmationDialog(it)
                }
            })
            nonOptionalViewModel.taskDeletedEvent.observe(this@TaskDetailsFragment, Observer {
                showRootMessage(R.string.task_details_screen_message_deleted)
                conductor.goBack()
            })
            nonOptionalViewModel.start(arguments?.getString(PROVIDED_TASK_ID)
                    ?: "", tasksDataSource, reminderManager)
        }
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        val viewModel = viewDataBinding.viewModel?.let { it } ?: return
        viewModel.errorEvent.observe(this, errorObserver)
    }

    override fun onPause() {
        super.onPause()
        val viewModel = viewDataBinding.viewModel?.let { it } ?: return
        viewModel.errorEvent.removeObserver(errorObserver)
    }

    override val rootView: View
        get() = viewDataBinding.detailsRoot

    override fun getScreenTag(): String {
        return TAG
    }

    private fun showDeleteConfirmationDialog(task: Task) {
        val builder = AlertDialog.Builder(activity!!, R.style.AppAlertDialogStyle)
        val dialog = builder
                .setTitle(R.string.task_details_screen_removal_dialog_title)
                .setMessage(getString(R.string.task_details_screen_removal_dialog_mesage, task.title))
                .setPositiveButton(R.string.task_details_screen_removal_dialog_ok) { _, _ ->
                    viewDataBinding.viewModel?.confirmDeletion()
                }
                .setNegativeButton(R.string.task_details_screen_removal_dialog_cancel) { _, _ ->
                }.create()
        dialog.show()
    }
}