package com.dnod.tasksmanajinnee.manager

interface AuthManager {

    interface AuthCallback {

        fun onAuthSucceed()

        fun onAuthFailure(errorMessage: String)
    }

    fun auth(userName: String, password: String, callback: AuthCallback)

    fun isAuthorized(): Boolean
}