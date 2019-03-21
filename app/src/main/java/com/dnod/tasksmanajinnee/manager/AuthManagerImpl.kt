package com.dnod.tasksmanajinnee.manager

import android.annotation.SuppressLint
import com.dnod.tasksmanajinnee.data.remote.ClientApi
import com.dnod.tasksmanajinnee.data.remote.response.ErrorResponse
import com.dnod.tasksmanajinnee.utils.PreferencesUtil
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class AuthManagerImpl @Inject constructor(
    private val clientApi: ClientApi,
    private val gson: Gson
) : AuthManager {
    private val SESSION_TOKEN_KEY = "session_token"
    private var isAuthorized: Boolean = false

    init {
        val token = PreferencesUtil.getString(SESSION_TOKEN_KEY, null)
        if (token != null) {
            isAuthorized = true
            clientApi.setSessionToken(token)
        }
    }

    @SuppressLint("CheckResult")
    override fun auth(userName: String, password: String, callback: AuthManager.AuthCallback) {
        clientApi.login(userName, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val token = it.body()?.token
                if (token == null) {
                    val errorBody = it.errorBody()?.string() ?: "{}"
                    val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                    callback.onAuthFailure(errorResponse.fields["email"]?.get(0) ?: errorResponse.errorMessage)
                    return@subscribe
                }
                PreferencesUtil.putString(SESSION_TOKEN_KEY, token)
                isAuthorized = true
                callback.onAuthSucceed()
            }) { throwable ->
                callback.onAuthFailure(throwable.localizedMessage)
            }
    }

    override fun isAuthorized(): Boolean {
        return isAuthorized
    }
}