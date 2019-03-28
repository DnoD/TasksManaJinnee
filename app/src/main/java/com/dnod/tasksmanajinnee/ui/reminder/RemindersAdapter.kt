package com.dnod.tasksmanajinnee.ui.reminder

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.databinding.ItemReminderBinding

class RemindersAdapter(
        context: Context,
        private val listener: Listener
) : RecyclerView.Adapter<RemindersAdapter.ReminderHolder>() {

    private val data: ArrayList<Pair<Task, Int>> = ArrayList()
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    fun swapData(data: List<Pair<Task, Int>>) {
        this.data.clear()
        this.data.trimToSize()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ReminderHolder {
        return ReminderHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_reminder, parent, false), data, listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ReminderHolder, position: Int) {
        holder.bind(data.get(position))
    }

    interface Listener {

        fun onDelete(task: Task)
    }

    data class ReminderHolder(
            val binding: ItemReminderBinding,
            private val data: List<Pair<Task, Int>>,
            private val listener: Listener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.viewModel = ReminderItemViewModel()
            binding.btnDelete.setOnClickListener(this)
        }

        fun bind(data: Pair<Task, Int>) {
            binding.viewModel?.bind(data.first, data.second)
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            listener.onDelete(data[adapterPosition].first)
        }

    }
}