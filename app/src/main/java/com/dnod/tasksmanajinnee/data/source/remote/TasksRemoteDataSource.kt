package com.dnod.tasksmanajinnee.data.source.remote

import com.dnod.tasksmanajinnee.data.source.TasksDataSource
import com.dnod.tasksmanajinnee.data.source.remote.response.TasksResponse
import com.dnod.tasksmanajinnee.sorting.SortingProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class TasksRemoteDataSource @Inject constructor(
        private val clientApi: ClientApi,
        private val sortingProvider: SortingProvider
) : TasksDataSource {

    private val composite = CompositeDisposable()
    private var nextPage: Int? = 0

    override fun getTasks(listener: TasksDataSource.GetTasksListener) {
        composite.add(clientApi.getTasks(1, sortingProvider.getCurrentSortModel())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                handleTasksResponse(response, listener)
            }) {
                listener.onReceiveTasksFailure()
            })
    }

    override fun getNextPage(listener: TasksDataSource.GetTasksListener) {
        nextPage?.let {
            composite.add(clientApi.getTasks(it, sortingProvider.getCurrentSortModel())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    handleTasksResponse(response, listener)
                }) {
                    listener.onReceiveTasksFailure()
                })
        }
    }

    override fun getTask(taskid: String, listener: TasksDataSource.GetTaskListener) {
        composite.add(clientApi.getTask(taskid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                        listener.onTaskNotFound()
                        return@subscribe
                    }
                    val task = response.body()
                    if (response.errorBody() != null || task == null) {
                        listener.onReceiveTaskFailure()
                        return@subscribe
                    }
                    listener.onReceiveTask(task.task)
                }) {
                    listener.onReceiveTaskFailure()
                })
    }

    private fun handleTasksResponse(response: Response<TasksResponse>, listener: TasksDataSource.GetTasksListener) {
        if (response.errorBody() != null || response.body() == null) {
            listener.onReceiveTasksFailure()
            return
        }
        val body = response.body() ?: return
        nextPage = if (body.meta.count / body.meta.current > body.meta.limit) body.meta.current + 1 else null
        listener.onReceiveTasks(body.tasks, nextPage != null)
    }
}