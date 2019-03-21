package com.dnod.tasksmanajinnee.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

object DateFormatUtil {

    @SuppressLint("SimpleDateFormat")
    fun getTaskDue(date: Date): String {
        val formatter = SimpleDateFormat("MM/dd/yy")
        return formatter.format(date)
    }
}
