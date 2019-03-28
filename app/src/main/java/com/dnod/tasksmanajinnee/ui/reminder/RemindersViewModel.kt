package com.dnod.tasksmanajinnee.ui.reminder

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.manager.ReminderManager
import com.dnod.tasksmanajinnee.ui.SingleEvent
import com.dnod.tasksmanajinnee.ui.view.ToolBarViewModel

class RemindersViewModel : ViewModel(), ToolBarViewModel.Listener {

    private lateinit var reminderManager: ReminderManager

    internal val backAction = SingleEvent<Void>()

    val isDataAvailable = ObservableBoolean(true)
    val reminders = ObservableField<List<Pair<Task, Int>>>()
    val toolbarViewModel = ObservableField<ToolBarViewModel>()

    fun start(reminderManager: ReminderManager) {
        toolbarViewModel.set(ToolBarViewModel(R.string.reminders_screen_title, R.drawable.ic_black, null, this))
        this.reminderManager = reminderManager
        refresh()
    }

    fun refresh() {
        reminders.set(reminderManager.getReminders())
        isDataAvailable.set(reminders.get()?.isNotEmpty() ?: false)
    }

    override fun onLeftAction() {
        backAction.call()
    }

    override fun onRightAction() {
    }
}
