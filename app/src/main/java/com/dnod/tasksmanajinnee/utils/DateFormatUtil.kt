package com.dnod.tasksmanajinnee.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtil {
    private val UNIX_MIN_YEAR_VALUE = 1970
    private val UNIX_TIME_MULTIPLIER = 1000L          //For more info see: http://stackoverflow.com/questions/732034/getting-unixtime-in-java

    @SuppressLint("SimpleDateFormat")
    fun getTaskDue(date: Date): String {
        val formatter = SimpleDateFormat("MM/dd/yy")
        return formatter.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun getFullTaskDue(date: Date): String {
        val formatter = SimpleDateFormat("EEEE dd MMM, yyyy")
        return formatter.format(date)
    }

    fun getFixedTime(time: Long): Long {
        return DateFormatUtil.getFixedTime(time, false)
    }

    fun getFixedTime(time: Long, force: Boolean): Long {
        var time = time
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        //This line will fix UNIX time compatibility
        if (force || calendar.get(Calendar.YEAR) <= UNIX_MIN_YEAR_VALUE) {
            time *= UNIX_TIME_MULTIPLIER
        }
        return time
    }
}
