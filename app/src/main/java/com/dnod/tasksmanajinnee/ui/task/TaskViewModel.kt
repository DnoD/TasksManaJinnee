package com.dnod.tasksmanajinnee.ui.task

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.*
import com.dnod.tasksmanajinnee.data.source.TasksDataSource
import com.dnod.tasksmanajinnee.ui.*
import com.dnod.tasksmanajinnee.ui.base.BaseViewModel
import com.dnod.tasksmanajinnee.ui.view.ToolBarViewModel
import com.dnod.tasksmanajinnee.utils.DateFormatUtil
import java.util.*

class TaskViewModel : BaseViewModel(), ToolBarViewModel.Listener, TasksDataSource.GetTaskListener, TasksDataSource.TaskCreateListener, TasksDataSource.TaskUpdateListener {

    private lateinit var tasksDataSource: TasksDataSource
    private var task: Task? = null
    private var isCreationWork = false
    private var taskTitle = ""
    private var taskPriority = ""
    private var taskDescription = ""
    private var taskDueTo = 0L
    private var taskNotificationMinutes = 0

    internal val pickNotificationAction = SingleEvent<Int>()
    internal val backAction = SingleEvent<Void>()
    internal val pickDueToAction = SingleEvent<Long>()
    internal val saveSucceedEvent = SingleEvent<Void>()
    internal val errorEvent = SingleEvent<String>()

    val isHighPriority = ObservableBoolean()
    val isMediumPriority = ObservableBoolean()
    val isLowPriority = ObservableBoolean()
    val title = ObservableField<String>()
    val dueTo = ObservableField<String>()
    val priority = ObservableField<String>()
    val description = ObservableField<String>()
    val notification = ObservableField<String>(getString(R.string.common_label_none))
    val priorityTextColor = ObservableField<Int>()

    val toolbarViewModel = ObservableField<ToolBarViewModel>()

    fun start(taskId: String, tasksDataSource: TasksDataSource) {
        this.tasksDataSource = tasksDataSource
        toolbarViewModel.set(ToolBarViewModel(if (taskId.isEmpty()) R.string.task_create_update_screen_title_create else R.string.task_create_update_screen_title_update,
                R.drawable.ic_black, R.drawable.ic_check, this))
        isCreationWork = taskId.isEmpty()
        if (!isCreationWork) {
            tasksDataSource.getTask(taskId, this)
        }
    }

    fun setDueTo(calendar: Calendar) {
        taskDueTo = calendar.timeInMillis
        dueTo.set(DateFormatUtil.getTaskDue(calendar.time))
    }

    fun setNotificationValue(minutes: Int) {
        applyNotificationValue(minutes)
    }

    fun pickDueTo() {
        pickDueToAction.postValue(taskDueTo)
    }

    fun pickNotification() {
        pickNotificationAction.postValue(taskNotificationMinutes)
    }

    fun onTitleChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        taskTitle = s.toString()
    }

    fun onDescriptionChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        taskDescription = s.toString()
    }

    fun onPriorityHighChanged(view: View, checked: Boolean) {
        if (checked) {
            taskPriority = TaskPriority.HIGHT.name
        }
    }

    fun onPriorityMediumChanged(view: View, checked: Boolean) {
        if (checked) {
            taskPriority = TaskPriority.NORMAL.name
        }
    }

    fun onPriorityLowChanged(view: View, checked: Boolean) {
        if (checked) {
            taskPriority = TaskPriority.LOW.name
        }
    }

    override fun onTaskCreated(task: Task) {
        saveSucceedEvent.call()
    }

    override fun onTaskCreateFailure() {
        errorEvent.postValue(getString(R.string.task_create_update_screen_message_action_failure))
    }

    override fun onTaskUpdated(task: Task) {
        saveSucceedEvent.call()
    }

    override fun onTaskUpdateFailure() {
        errorEvent.postValue(getString(R.string.task_create_update_screen_message_action_failure))
    }

    override fun onLeftAction() {
        backAction.call()
    }

    override fun onRightAction() {
        if (validateForm()) {
            if (isCreationWork) {
                tasksDataSource.create(Task(null, taskTitle, taskDescription, taskDueTo.toString(), TaskPriority.valueOf(taskPriority)), this)
            } else {
                prepareUpdatedTask()?.let {
                    tasksDataSource.update(it, this)
                    return
                }
                saveSucceedEvent.call()
            }
        } else {
            errorEvent.postValue(getString(R.string.task_create_update_screen_message_empty_fields))
        }
    }

    override fun onReceiveTask(task: Task) {
        this.task = task
        isHighPriority.set(task.priority == TaskPriority.HIGHT)
        isMediumPriority.set(task.priority == TaskPriority.NORMAL)
        isLowPriority.set(task.priority == TaskPriority.LOW)
        title.set(task.title)
        dueTo.set(task.getFormattedDueBy())
        priority.set(task.getPriorityString())
        priorityTextColor.set(getColor(when (task.priority) {
            TaskPriority.NORMAL -> R.color.priority_normal_color
            TaskPriority.HIGHT -> R.color.priority_high_color
            TaskPriority.LOW -> R.color.priority_low_color
        }))
        description.set(task.description)
    }

    override fun onTaskNotFound() {
        errorEvent.postValue(getString(R.string.common_error_messages_unexpected_server_response))
    }

    override fun onReceiveTaskFailure() {
        errorEvent.postValue(getString(R.string.common_error_messages_unexpected_server_response))
    }

    private fun validateForm(): Boolean {
        if (isCreationWork) {
            return taskTitle.isNotEmpty() &&
                    taskPriority.isNotEmpty() &&
                    taskDescription.isNotEmpty() &&
                    taskDueTo > 0L
        }
        return taskTitle.isNotEmpty() &&
                taskDescription.isNotEmpty()
    }

    private fun prepareUpdatedTask(): Task? {
        task?.let {
            val titleChanged = it.title != taskTitle
            val priorityChanged = taskPriority.isNotEmpty() && it.priority != TaskPriority.valueOf(taskPriority)
            val dueChanged = taskDueTo != 0L && it.dueBy != taskDueTo.toString()
            val descriptionChanged = it.description != taskDescription
            if (titleChanged ||
                    priorityChanged ||
                    dueChanged ||
                    descriptionChanged) {
                return Task(it.id,
                        if (titleChanged) taskTitle else it.title,
                        if (descriptionChanged) taskDescription else it.description,
                        if (dueChanged) DateFormatUtil.getFixedTime(taskDueTo, true).toString() else it.dueBy,
                        if (priorityChanged) TaskPriority.valueOf(taskPriority) else it.priority)
            }
        }
        return null
    }

    private fun applyNotificationValue(minutes: Int) {
        taskNotificationMinutes = minutes
        notification.set(getStringArray(R.array.notification_options_labels)[getIntArray(R.array.notification_options_values).indexOfFirst { value -> value == minutes }])
    }
}
