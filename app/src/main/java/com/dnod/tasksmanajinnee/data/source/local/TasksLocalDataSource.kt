package com.dnod.tasksmanajinnee.data.source.local

import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.data.source.TasksDataSource
import javax.inject.Inject

/**
 * Data source class for further local data caching, let's keep it empty for the MVP
 */
class TasksLocalDataSource @Inject constructor(
) : TasksDataSource {

    override fun getTasks(listener: TasksDataSource.GetTasksListener) {
    }

    override fun getNextPage(listener: TasksDataSource.GetTasksListener) {
    }

    override fun getTask(taskid: String, listener: TasksDataSource.GetTaskListener) {
    }

    override fun delete(task: Task, listener: TasksDataSource.TaskDeleteListener) {
    }

    override fun create(task: Task, listener: TasksDataSource.TaskCreateListener) {
    }

    override fun update(task: Task, listener: TasksDataSource.TaskUpdateListener) {
    }
}