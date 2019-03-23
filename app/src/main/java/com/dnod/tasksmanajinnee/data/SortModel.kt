package com.dnod.tasksmanajinnee.data

data class SortModel(
        var value: Value,
        var type: Type
) {

    enum class Value {
        NONE, NAME, PRIORITY, DATE
    }

    enum class Type {
        NONE, ASC, DESC
    }
}