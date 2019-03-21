package com.dnod.tasksmanajinnee.data

import com.dnod.tasksmanajinnee.utils.DateFormatUtil
import java.util.*

fun Task.getFormattedDueBy(): String {
    val time = Calendar.getInstance()
    time.timeInMillis = dueBy.toLong()
    return DateFormatUtil.getTaskDue(time.time)
}