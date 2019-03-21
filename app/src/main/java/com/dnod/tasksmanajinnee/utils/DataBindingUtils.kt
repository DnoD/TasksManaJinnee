package com.dnod.tasksmanajinnee.utils

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.ui.tasks.TasksAdapter
import com.dnod.tasksmanajinnee.ui.view.PaginateRecycleView
import com.paginate.Paginate

@BindingAdapter("data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, items: List<*>?) {
    items?.let {
        var adapter = recyclerView.adapter
        if (adapter is TasksAdapter) {
            adapter.swapData(items as List<Task>)
        }
    }
}

@BindingAdapter("pageData")
fun <T> updateRecyclerViewProperties(recyclerView: RecyclerView, items: List<*>?) {
    items?.let {
        var adapter = recyclerView.adapter
        if (adapter is TasksAdapter) {
            adapter.addAll(items as List<Task>)
        }
    }
}

@BindingAdapter("paginateListener")
fun <T> setPaginateListener(recyclerView: PaginateRecycleView, listener: Paginate.Callbacks?) {
    listener?.let {
        recyclerView.setPaginateListener(it)
    }
}