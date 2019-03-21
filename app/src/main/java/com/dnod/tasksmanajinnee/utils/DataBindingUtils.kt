package com.dnod.tasksmanajinnee.utils

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.ui.tasks.TasksAdapter

@BindingAdapter("data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, items: List<*>?) {
    items?.let {
        var adapter = recyclerView.adapter
        if (adapter is TasksAdapter) {
            adapter.swapData(items as List<Task>)
        }
    }
}