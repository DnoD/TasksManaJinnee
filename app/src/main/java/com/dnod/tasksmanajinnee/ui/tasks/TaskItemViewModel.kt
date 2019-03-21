package com.dnod.tasksmanajinnee.ui.tasks

import android.databinding.ObservableField
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.data.TaskPriority
import com.dnod.tasksmanajinnee.data.getFormattedDueBy
import com.dnod.tasksmanajinnee.ui.base.BaseViewModel
import com.dnod.tasksmanajinnee.ui.getColor
import com.dnod.tasksmanajinnee.ui.getString

class TaskItemViewModel : BaseViewModel() {

    val title = ObservableField<String>("")
    val dueToLabel = ObservableField<String>("")
    val priorityLabel = ObservableField<String>("")
    val priorityLabelTextColor = ObservableField<Int>(getColor(R.color.priority_normal_color))

    fun bind(task: Task) {
        title.set(task.title)
        dueToLabel.set(getString(R.string.tasks_screen_label_due_to, task.getFormattedDueBy()))
        priorityLabel.set(getString(when (task.priority) {
            TaskPriority.NORMAL -> R.string.tasks_screen_label_medium
            TaskPriority.HIGHT -> R.string.tasks_screen_label_high
            TaskPriority.LOW -> R.string.tasks_screen_label_low
        }))
        priorityLabelTextColor.set(getColor(when (task.priority) {
            TaskPriority.NORMAL -> R.color.priority_normal_color
            TaskPriority.HIGHT -> R.color.priority_high_color
            TaskPriority.LOW -> R.color.priority_low_color
        }))
    }
}