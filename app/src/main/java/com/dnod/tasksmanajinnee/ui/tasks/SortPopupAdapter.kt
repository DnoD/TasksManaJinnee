package com.dnod.tasksmanajinnee.ui.tasks

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.SortModel
import com.dnod.tasksmanajinnee.databinding.ItemSortMenuBinding

class SortPopupAdapter(
        context: Context?,
        selected: SortModel,
        private val objects: MutableList<SortModel>
) : ArrayAdapter<SortModel>(context, R.layout.item_sort_menu, objects) {

    private val layoutInflater = LayoutInflater.from(context)
    private var selectedSortModel = selected

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val sortItem = objects[position]
        val viewBinding = if (convertView == null)
            DataBindingUtil.inflate(layoutInflater, R.layout.item_sort_menu, parent, false) as ItemSortMenuBinding else
            DataBindingUtil.bind<ItemSortMenuBinding>(convertView)
        viewBinding?.let {
            it.text.text = when (sortItem.value) {
                SortModel.Value.NONE -> context.getString(R.string.sort_value_none)
                SortModel.Value.DATE -> context.getString(R.string.sort_value_date)
                SortModel.Value.NAME -> context.getString(R.string.sort_value_name)
                SortModel.Value.PRIORITY -> context.getString(R.string.sort_value_priority)
            }
            it.icon.visibility = if (sortItem.value == SortModel.Value.NONE || !isSelectedItem(sortItem)) View.INVISIBLE else View.VISIBLE
            it.icon.setImageResource(
                    when (selectedSortModel.type) {
                        SortModel.Type.NONE,
                        SortModel.Type.ASC -> R.drawable.ic_arrow_ascending
                        SortModel.Type.DESC -> R.drawable.ic_arrow_descending
                    }
            )
        }
        return viewBinding?.root ?: super.getView(position, convertView, parent)
    }

    fun setSelectedSortModel(sortModel: SortModel) {
        selectedSortModel = sortModel
    }

    private fun isSelectedItem(sortModel: SortModel): Boolean {
        return selectedSortModel.value == sortModel.value
    }
}