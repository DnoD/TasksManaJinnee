package com.dnod.tasksmanajinnee.data.source.remote

import com.dnod.tasksmanajinnee.data.SortModel
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.data.source.remote.response.AuthResponse
import com.dnod.tasksmanajinnee.data.source.remote.response.TaskResponse
import com.dnod.tasksmanajinnee.data.source.remote.response.TasksResponse
import io.reactivex.Observable
import retrofit2.Response

interface ClientApi {

    fun login(userName: String, password: String): Observable<Response<AuthResponse>>

    fun register(userName: String, password: String): Observable<Response<AuthResponse>>

    fun getTasks(page: Int, sortModel: SortModel): Observable<Response<TasksResponse>>

    fun getTask(taskId: String): Observable<Response<TaskResponse>>

    fun setSessionToken(token: String)

    fun deleteTask(task: Task): Observable<Response<Void>>

    fun createTask(task: Task): Observable<Response<TaskResponse>>

    fun updateTask(task: Task): Observable<Response<TaskResponse>>
}
