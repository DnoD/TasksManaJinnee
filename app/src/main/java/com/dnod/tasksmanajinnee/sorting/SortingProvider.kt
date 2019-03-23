package com.dnod.tasksmanajinnee.sorting

import com.dnod.tasksmanajinnee.data.SortModel

interface SortingProvider {

    fun getAvailableSortValues(): ArrayList<SortModel>

    fun getCurrentSortModel(): SortModel
}