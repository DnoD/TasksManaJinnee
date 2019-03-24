package com.dnod.tasksmanajinnee.data.source.remote

import com.dnod.tasksmanajinnee.data.source.remote.request.AuthRequest
import com.dnod.tasksmanajinnee.data.source.remote.response.AuthResponse
import com.dnod.tasksmanajinnee.data.source.remote.response.TaskResponse
import com.dnod.tasksmanajinnee.data.source.remote.response.TasksResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ManaJinneeService {

    @Headers("cache-control: no-cache",
        "Content-Type: application/json")
    @POST("auth")
    fun login(@Body request: AuthRequest): Observable<Response<AuthResponse>>

    @Headers("cache-control: no-cache",
        "Content-Type: application/json")
    @POST("users")
    fun register(@Body request: AuthRequest): Observable<Response<AuthResponse>>

    @GET("tasks")
    fun getTasks(@Query("page") page: Int, @Query("sort") sort: String?): Observable<Response<TasksResponse>>

    @GET("tasks/{id}")
    fun getTask(@Path("id") taskId: String): Observable<Response<TaskResponse>>
}
