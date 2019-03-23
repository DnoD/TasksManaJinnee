package com.dnod.tasksmanajinnee.manager

import com.dnod.tasksmanajinnee.data.SortModel
import com.dnod.tasksmanajinnee.sorting.SortingProvider
import javax.inject.Inject

class SortingManagerImpl @Inject constructor(
) : SortingManager, SortingProvider {

    private val sortData = arrayListOf(
            SortModel(SortModel.Value.NONE, SortModel.Type.NONE),
            SortModel(SortModel.Value.NAME, SortModel.Type.NONE),
            SortModel(SortModel.Value.PRIORITY, SortModel.Type.NONE),
            SortModel(SortModel.Value.DATE, SortModel.Type.NONE)
    )
    private var selectedSortModel: SortModel = sortData[0]

    override fun getAvailableSortValues(): ArrayList<SortModel> {
        return sortData
    }

    override fun getCurrentSortModel(): SortModel {
        return selectedSortModel
    }

    override fun applySortModel(sortModel: SortModel): SortModel {
        if (sortModel.value != SortModel.Value.NONE) {
            if (sortModel.value == selectedSortModel.value) {
                this.selectedSortModel.type = if (selectedSortModel.type == SortModel.Type.ASC) SortModel.Type.DESC else SortModel.Type.ASC
            } else {
                this.selectedSortModel = sortModel
                this.selectedSortModel.type = SortModel.Type.ASC
            }
        } else {
            this.selectedSortModel = sortModel
        }
        return selectedSortModel
    }
}