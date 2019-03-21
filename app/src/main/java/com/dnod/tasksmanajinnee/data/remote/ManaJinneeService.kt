package com.dnod.tasksmanajinnee.data.remote

import com.dnod.tasksmanajinnee.data.remote.request.AuthRequest
import com.dnod.tasksmanajinnee.data.remote.response.AuthResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ManaJinneeService {

    @Headers("cache-control: no-cache",
        "Content-Type: application/json")
    @POST("auth")
    fun login(@Body request: AuthRequest): Observable<Response<AuthResponse>>

    @Headers("cache-control: no-cache",
        "Content-Type: application/json")
    @POST("/users")
    fun register(@Body request: AuthRequest): Observable<Response<AuthResponse>>
}
