package com.dnod.tasksmanajinnee.data

data class SortModel(
        val value: Value,
        val type: Type
) {

    enum class Value {
        NONE, NAME, PRIORITY, DATE
    }

    enum class Type {
        NONE, ASC, DESC
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SortModel) return false

        if (value != other.value) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }


}