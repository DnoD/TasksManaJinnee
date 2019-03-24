package com.dnod.tasksmanajinnee.data.source

import com.dnod.tasksmanajinnee.data.source.local.LocalTasks
import com.dnod.tasksmanajinnee.data.source.remote.RemoteTasks
import javax.inject.Inject

class TasksRepository @Inject constructor(
        @RemoteTasks private val remoteDataSource: TasksDataSource,
        @LocalTasks private val localDataSource: TasksDataSource
) : TasksDataSource {

    override fun getTasks(listener: TasksDataSource.GetTasksListener) {
        remoteDataSource.getTasks(listener)
    }

    override fun getNextPage(listener: TasksDataSource.GetTasksListener) {
        remoteDataSource.getNextPage(listener)
    }

    override fun getTask(taskid: String, listener: TasksDataSource.GetTaskListener) {
        remoteDataSource.getTask(taskid, listener)
    }
}