package com.dnod.tasksmanajinnee.data.source.remote.response

import com.dnod.tasksmanajinnee.data.Task
import com.google.gson.annotations.SerializedName

data class TaskResponse(
        @SerializedName("task")
        var task: Task = Task()

)