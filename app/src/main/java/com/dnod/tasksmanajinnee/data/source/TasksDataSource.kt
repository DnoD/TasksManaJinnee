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

    interface TaskDeleteListener {
        fun onTaskDeleted(task: Task)

        fun onTaskDeleteFailure()
    }

    interface TaskCreateListener {
        fun onTaskCreated(task: Task)

        fun onTaskCreateFailure()
    }

    interface TaskUpdateListener {
        fun onTaskUpdated(task: Task)

        fun onTaskUpdateFailure()
    }

    fun getTasks(listener: GetTasksListener)

    fun getTask(taskid: String, listener: GetTaskListener)

    fun getNextPage(listener: GetTasksListener)

    fun delete(task: Task, listener: TaskDeleteListener)

    fun create(task: Task, listener: TaskCreateListener)

    fun update(task: Task, listener: TaskUpdateListener)
}