package com.dnod.tasksmanajinnee.ui.tasks

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.data.TaskPriority
import com.dnod.tasksmanajinnee.data.source.TasksDataSource
import com.dnod.tasksmanajinnee.ui.SingleEvent
import com.paginate.Paginate
import java.util.*

class TasksViewModel : ViewModel(), Paginate.Callbacks, TasksDataSource.GetTasksListener {

    private lateinit var tasksDataSource: TasksDataSource
    private var hasNextPage = false
    private var isNextPageLoading = false
    private var isRefreshRequested = false

    internal val alertAction = SingleEvent<Void>()
    internal val sortAction = SingleEvent<Void>()
    internal val createTaskAction = SingleEvent<Void>()

    val isDataAvailable = ObservableBoolean(true)
    val isDataLoading = ObservableBoolean(false)
    val isErrorOccurred = ObservableBoolean(false)
    val tasks = ObservableField<List<Task>>()
    val pageTasks = ObservableField<List<Task>>()
    val paginateListener = ObservableField<Paginate.Callbacks>()

    fun start(tasksDataSource: TasksDataSource) {
        this.tasksDataSource = tasksDataSource
        tasks.set(emptyList())
        showLoadingState()
        isRefreshRequested = true
        tasksDataSource.getTasks(this)
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
        isRefreshRequested = true
        showLoadingState()
        tasksDataSource.getTasks(this)
    }

    override fun onReceiveTasks(tasks: List<Task>, hasNextPage: Boolean) {
        isNextPageLoading = false
        this.hasNextPage = hasNextPage
        isDataAvailable.set(tasks.isNotEmpty())
        isDataLoading.set(false)
        if (isRefreshRequested) {
            isRefreshRequested = false
            this.tasks.set(tasks)
        } else {
            pageTasks.set(tasks)
        }
        paginateListener.set(this)
    }

    override fun onReceiveTasksFailure() {
        isDataAvailable.set(false)
        isDataLoading.set(false)
        isErrorOccurred.set(true)
    }

    override fun onLoadMore() {
        isNextPageLoading = true
        tasksDataSource.getNextPage(this)
    }

    override fun isLoading(): Boolean {
        return isNextPageLoading
    }

    override fun hasLoadedAllItems(): Boolean {
        return !hasNextPage
    }

    private fun showLoadingState() {
        isDataLoading.set(true)
        isErrorOccurred.set(false)
    }
}
