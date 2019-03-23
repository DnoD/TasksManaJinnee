package com.dnod.tasksmanajinnee.manager

import com.dnod.tasksmanajinnee.data.SortModel

interface SortingManager {

    fun applySortModel(sortModel: SortModel) : SortModel
}