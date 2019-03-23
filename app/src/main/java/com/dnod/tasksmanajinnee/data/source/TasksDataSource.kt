package com.dnod.tasksmanajinnee.data.source

import com.dnod.tasksmanajinnee.data.Task

interface TasksDataSource {

    interface GetTasksListener {
        fun onReceiveTasks(tasks: List<Task>, hasNextPage: Boolean)

        fun onReceiveTasksFailure()
    }

    fun getTasks(listener: GetTasksListener)

    fun getNextPage(listener: GetTasksListener)
}