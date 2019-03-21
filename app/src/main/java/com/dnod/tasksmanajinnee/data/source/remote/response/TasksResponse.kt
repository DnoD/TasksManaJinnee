package com.dnod.tasksmanajinnee.data.source.remote.response

import com.dnod.tasksmanajinnee.data.Task
import com.google.gson.annotations.SerializedName

data class TasksResponse(
        @SerializedName("tasks")
        var tasks: List<Task> = emptyList(),
        @SerializedName("meta")
        var meta: Meta = Meta()

) {
    data class Meta(
            @SerializedName("current")
            var current: Int = 0,
            @SerializedName("count")
            var count: Int = 0,
            @SerializedName("limit")
            var limit: Int = 0
    )
}