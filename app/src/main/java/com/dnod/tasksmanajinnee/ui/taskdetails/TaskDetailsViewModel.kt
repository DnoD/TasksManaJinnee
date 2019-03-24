package com.dnod.tasksmanajinnee.ui.taskdetails

import android.databinding.ObservableField
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.*
import com.dnod.tasksmanajinnee.data.source.TasksDataSource
import com.dnod.tasksmanajinnee.ui.SingleEvent
import com.dnod.tasksmanajinnee.ui.base.BaseViewModel
import com.dnod.tasksmanajinnee.ui.getColor
import com.dnod.tasksmanajinnee.ui.getString
import com.dnod.tasksmanajinnee.ui.view.ToolBarViewModel

class TaskDetailsViewModel : BaseViewModel(), ToolBarViewModel.Listener, TasksDataSource.GetTaskListener, TasksDataSource.TaskDeleteListener {

    private lateinit var tasksDataSource: TasksDataSource
    private var task: Task? = null

    internal val backAction = SingleEvent<Void>()
    internal val editAction = SingleEvent<Void>()
    internal val deleteAction = SingleEvent<Task>()
    internal val taskDeletedEvent = SingleEvent<Void>()
    internal val errorEvent = SingleEvent<String>()

    val title = ObservableField<String>()
    val due = ObservableField<String>()
    val priority = ObservableField<String>()
    val description = ObservableField<String>()
    val notification = ObservableField<String>(getString(R.string.common_label_none))
    val priorityTextColor = ObservableField<Int>()

    val toolbarViewModel = ObservableField<ToolBarViewModel>()

    fun start(taskId: String, tasksDataSource: TasksDataSource) {
        this.tasksDataSource = tasksDataSource
        toolbarViewModel.set(ToolBarViewModel(R.string.task_details_screen_title, R.drawable.ic_black, R.drawable.ic_edit, this))
        tasksDataSource.getTask(taskId, this)
    }

    override fun onLeftAction() {
        backAction.call()
    }

    override fun onRightAction() {
        editAction.call()
    }

    fun delete() {
        deleteAction.postValue(task)
    }

    fun confirmDeletion() {
        task?.let {
            tasksDataSource.delete(it, this)
        }
    }

    override fun onReceiveTask(task: Task) {
        this.task = task
        title.set(task.title)
        due.set(task.getFullFormattedDueBy())
        priority.set(task.getPriorityString())
        priorityTextColor.set(getColor(when (task.priority) {
            TaskPriority.NORMAL -> R.color.priority_normal_color
            TaskPriority.HIGHT -> R.color.priority_high_color
            TaskPriority.LOW -> R.color.priority_low_color
        }))
        description.set(task.description)
    }

    override fun onTaskNotFound() {
        taskDeletedEvent.call()
    }

    override fun onReceiveTaskFailure() {
        errorEvent.postValue(getString(R.string.common_error_messages_unexpected_server_response))
    }

    override fun onTaskDeleted(task: Task) {
        taskDeletedEvent.call()
    }

    override fun onTaskDeleteFailure() {
        errorEvent.postValue(getString(R.string.common_error_messages_unexpected_server_response))
    }
}
