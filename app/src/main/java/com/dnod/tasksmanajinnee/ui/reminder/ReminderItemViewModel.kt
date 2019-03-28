package com.dnod.tasksmanajinnee.ui.reminder

import android.databinding.ObservableField
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.ui.base.BaseViewModel
import com.dnod.tasksmanajinnee.ui.getIntArray
import com.dnod.tasksmanajinnee.ui.getString
import com.dnod.tasksmanajinnee.ui.getStringArray

class ReminderItemViewModel : BaseViewModel() {

    val title = ObservableField<String>("")
    val subTitle = ObservableField<String>("")

    fun bind(task: Task, minutes: Int) {
        title.set(task.title)
        subTitle.set(if (minutes < 60) getString(R.string.reminder_notification_minutes_body_format, minutes.toString()) else getString(R.string.reminders_screen_label_hour))
    }
}