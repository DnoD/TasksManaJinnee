package com.dnod.tasksmanajinnee.data

import com.dnod.tasksmanajinnee.ManaJinneeApplication
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.utils.DateFormatUtil
import java.util.*

fun Task.getFormattedDueBy(): String {
    val time = Calendar.getInstance()
    time.timeInMillis = dueBy.toLong()
    return DateFormatUtil.getTaskDue(time.time)
}

fun Task.getFullFormattedDueBy(): String {
    val time = Calendar.getInstance()
    time.timeInMillis = dueBy.toLong()
    return DateFormatUtil.getFullTaskDue(time.time)
}

fun Task.getPriorityString(): String {
    return ManaJinneeApplication.instance.getString(when (priority) {
        TaskPriority.NORMAL -> R.string.tasks_screen_label_medium
        TaskPriority.HIGHT -> R.string.tasks_screen_label_high
        TaskPriority.LOW -> R.string.tasks_screen_label_low
    })
}