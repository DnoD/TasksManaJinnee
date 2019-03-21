package com.dnod.tasksmanajinnee.data.remote

import com.dnod.tasksmanajinnee.data.remote.response.AuthResponse
import io.reactivex.Observable
import retrofit2.Response

interface ClientApi {

    fun login(userName: String, password: String) : Observable<Response<AuthResponse>>

    fun register(userName: String, password: String) : Observable<Response<AuthResponse>>

    fun setSessionToken(token: String)
}
