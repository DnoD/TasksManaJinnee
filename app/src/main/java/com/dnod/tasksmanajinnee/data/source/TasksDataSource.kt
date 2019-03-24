package com.dnod.tasksmanajinnee.data.source

import com.dnod.tasksmanajinnee.data.Task

interface TasksDataSource {

    interface GetTasksListener {
        fun onReceiveTasks(tasks: List<Task>, hasNextPage: Boolean)

        fun onReceiveTasksFailure()
    }

    interface GetTaskListener {
        fun onReceiveTask(task: Task)

        fun onTaskNotFound()

        fun onReceiveTaskFailure()
    }

    fun getTasks(listener: GetTasksListener)

    fun getTask(taskid: String, listener: GetTaskListener)

    fun getNextPage(listener: GetTasksListener)
}