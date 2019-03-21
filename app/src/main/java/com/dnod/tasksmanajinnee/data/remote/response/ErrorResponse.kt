package com.dnod.tasksmanajinnee.data.remote.response

import com.google.gson.annotations.SerializedName

class ErrorResponse(
    @SerializedName("fields") var fields: Map<String, List<String>> = emptyMap(),
    @SerializedName("message") var errorMessage: String = ""
)