package com.dnod.tasksmanajinnee.manager

import com.dnod.tasksmanajinnee.data.Task

interface ReminderManager {

    fun getTaskReminderValue(taskId: String): Int

    fun applyTaskReminderValue(task: Task, minutes: Int): Boolean

    fun removeTaskReminderValue(task: Task)

    fun getReminders(): List<Pair<Task, Int>>
}