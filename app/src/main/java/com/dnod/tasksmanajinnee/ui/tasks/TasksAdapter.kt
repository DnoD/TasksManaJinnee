package com.dnod.tasksmanajinnee.ui.tasks

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.databinding.ItemTaskBinding

class TasksAdapter(
        context: Context,
        private val listener: Listener
) : RecyclerView.Adapter<TasksAdapter.TaskHolder>() {

    private val data: ArrayList<Task> = ArrayList()
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    fun addAll(data: List<Task>) {
        val prevSize = this.data.size
        this.data.addAll(data)
        notifyItemRangeInserted(prevSize, data.size)
    }

    fun swapData(data: List<Task>) {
        this.data.clear()
        this.data.trimToSize()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): TaskHolder {
        return TaskHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_task, parent, false), data, listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].id.hashCode()
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(data.get(position))
    }

    interface Listener {

        fun onItemClick(task: Task)
    }

    data class TaskHolder(
            val binding: ItemTaskBinding,
            private val data: List<Task>,
            private val listener: Listener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.viewModel = TaskItemViewModel()
            binding.root.setOnClickListener(this)
        }

        fun bind(task: Task) {
            binding.viewModel?.bind(task)
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            listener.onItemClick(data[adapterPosition])
        }

    }
}