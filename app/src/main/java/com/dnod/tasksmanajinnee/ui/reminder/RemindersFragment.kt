package com.dnod.tasksmanajinnee.ui.reminder

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.databinding.FragmentRemindersBinding
import com.dnod.tasksmanajinnee.manager.ReminderManager
import com.dnod.tasksmanajinnee.ui.Conductor
import com.dnod.tasksmanajinnee.ui.base.BaseFragment

import javax.inject.Inject

class RemindersFragment : BaseFragment(), RemindersAdapter.Listener {

    companion object {
        private val TAG = RemindersFragment::class.java.simpleName

        fun createInstance(): BaseFragment {
            val fragment = RemindersFragment()
            val arguments = Bundle()
            fragment.arguments = arguments
            return fragment
        }
    }

    private lateinit var viewDataBinding: FragmentRemindersBinding

    @Inject
    lateinit var conductor: Conductor<Conductor.ScreenBuilder<BaseFragment>>

    @Inject
    lateinit var reminderManager: ReminderManager

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reminders, container, false)
        viewDataBinding.apply {
            viewModel = ViewModelProviders.of(this@RemindersFragment).get(RemindersViewModel::class.java)
            val nonOptionalViewModel = viewModel?.let { it } ?: return@apply
            nonOptionalViewModel.backAction.observe(this@RemindersFragment, Observer {
                conductor.goBack()
            })
            setupRemindersList()
            nonOptionalViewModel.start(reminderManager)
        }
        return viewDataBinding.root
    }

    override fun onDelete(task: Task) {
        showDeleteConfirmationDialog(task)
    }

    override val rootView: View
        get() = viewDataBinding.remindersRoot

    override fun getScreenTag(): String {
        return TAG
    }

    private fun showDeleteConfirmationDialog(task: Task) {
        val builder = AlertDialog.Builder(activity!!, R.style.AppAlertDialogStyle)
        val dialog = builder
                .setTitle(R.string.reminders_screen_removal_dialog_title)
                .setMessage(getString(R.string.reminders_screen_removal_dialog_mesage))
                .setPositiveButton(R.string.task_details_screen_removal_dialog_ok) { _, _ ->
                    reminderManager.removeTaskReminderValue(task)
                    showMessage(R.string.reminders_screen_message_reminder_removed)
                    viewDataBinding.viewModel?.refresh()
                }
                .setNegativeButton(R.string.task_details_screen_removal_dialog_cancel) { _, _ ->
                }.create()
        dialog.show()
    }

    private fun setupRemindersList() {
        context?.let { it ->
            viewDataBinding.reminders.layoutManager = LinearLayoutManager(it)
            val dividerDecoration = DividerItemDecoration(it, DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(it, R.drawable.shape_list_divider)?.let {
                dividerDecoration.setDrawable(it)
            }
            viewDataBinding.reminders.addItemDecoration(dividerDecoration)
            viewDataBinding.reminders.adapter = RemindersAdapter(it, this@RemindersFragment)
        }
    }
}
