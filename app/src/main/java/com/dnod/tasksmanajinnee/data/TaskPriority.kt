package com.dnod.tasksmanajinnee.data

import com.google.gson.annotations.SerializedName

enum class TaskPriority {
    @SerializedName("Low")
    LOW,
    @SerializedName("Normal")
    NORMAL,
    @SerializedName("High")
    HIGHT
}