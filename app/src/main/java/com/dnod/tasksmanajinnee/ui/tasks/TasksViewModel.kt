package com.dnod.tasksmanajinnee.ui.tasks

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.data.TaskPriority
import com.dnod.tasksmanajinnee.ui.SingleEvent
import java.util.*

class TasksViewModel : ViewModel() {

    internal val alertAction = SingleEvent<Void>()
    internal val sortAction = SingleEvent<Void>()
    internal val createTaskAction = SingleEvent<Void>()

    val isDataAvailable = ObservableBoolean(true)
    val isDataLoading = ObservableBoolean(false)
    val isErrorOccurred = ObservableBoolean(false)
    val tasks = ObservableField<List<Task>>()

    fun start() {
    }

    fun createTask() {
        createTaskAction.call()
    }

    fun sort() {
        sortAction.call()
    }

    fun alert() {
        alertAction.call()
    }

    fun onRefresh() {
        isDataLoading.set(false)
    }

    fun onReceiveTasks(tasks: List<Task>) {
        isDataAvailable.set(tasks.isNotEmpty())
        isDataLoading.set(false)
        this.tasks.set(tasks)
    }

    fun onGetTasksFailure() {
        isDataAvailable.set(false)
        isDataLoading.set(false)
        isErrorOccurred.set(true)
    }
}
