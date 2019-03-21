package com.dnod.tasksmanajinnee.data.source.remote

import com.dnod.tasksmanajinnee.data.source.remote.response.AuthResponse
import com.dnod.tasksmanajinnee.data.source.remote.response.TasksResponse
import io.reactivex.Observable
import retrofit2.Response

interface ClientApi {

    fun login(userName: String, password: String) : Observable<Response<AuthResponse>>

    fun register(userName: String, password: String) : Observable<Response<AuthResponse>>

    fun getTasks(page: Int) : Observable<Response<TasksResponse>>

    fun setSessionToken(token: String)
}
