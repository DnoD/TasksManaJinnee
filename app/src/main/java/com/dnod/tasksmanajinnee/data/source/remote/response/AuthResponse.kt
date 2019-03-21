package com.dnod.tasksmanajinnee.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token") var token: String? = null
)