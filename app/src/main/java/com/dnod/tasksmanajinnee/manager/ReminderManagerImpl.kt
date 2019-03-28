package com.dnod.tasksmanajinnee.manager

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobScheduler
import android.content.Context
import com.dnod.tasksmanajinnee.data.Task
import com.dnod.tasksmanajinnee.data.TaskPriority
import com.dnod.tasksmanajinnee.utils.PreferencesUtil
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import javax.inject.Inject
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.content.ComponentName
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.PersistableBundle
import android.support.v4.app.NotificationCompat
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.service.ReminderService
import com.dnod.tasksmanajinnee.ui.main.MainActivity
import java.util.*
import java.util.concurrent.TimeUnit

class ReminderManagerImpl @Inject constructor(
        private val context: Context,
        private val gson: Gson
) : ReminderManager, ReminderService.JobHandler {

    private val STORED_REMINDERS = "stored_reminders"
    private val JOB_REMINDER_ID = "job_reminder_id"
    private val JOB_REMINDER_TITLE = "job_reminder_title"
    private val JOB_REMINDER_MINUTES = "job_reminder_minutes"
    private val reminders = ArrayList<TaskReminder>()

    init {
        reminders.addAll(gson.fromJson(PreferencesUtil.getString(STORED_REMINDERS, "[]"), Array<TaskReminder>::class.java).toList())
    }

    override fun getTaskReminderValue(taskId: String): Int {
        return reminders.firstOrNull { reminder -> reminder.taskId == taskId }?.remindValue ?: 0
    }

    override fun applyTaskReminderValue(task: Task, minutes: Int): Boolean {
        removeReminder(reminders.firstOrNull { item -> item.taskId == task.id })
        if (minutes == 0) {
            saveReminders()
            return true
        }
        return registerReminder(task, minutes).apply {
            if (this) {
                task.id?.let {
                    reminders.add(TaskReminder(it, task.title, task.dueBy.toLong(), minutes))
                    saveReminders()
                }
            }
        }
    }

    override fun removeTaskReminderValue(task: Task) {
        removeReminder(reminders.firstOrNull { item -> item.taskId == task.id })
        saveReminders()
    }

    override fun getReminders(): List<Pair<Task, Int>> {
        return reminders.map { reminder -> Pair(Task(reminder.taskId, reminder.taskTitle, null, reminder.taskDueTo.toString(), TaskPriority.LOW), reminder.remindValue) }
    }

    override fun handleJob(params: JobParameters?) {
        params?.let {
            removeReminder(reminders.firstOrNull { item -> item.taskId == it.extras.getString(JOB_REMINDER_ID, "") })
            showNotification(prepareNotification(it.extras.getString(JOB_REMINDER_TITLE, ""), it.extras.getInt(JOB_REMINDER_MINUTES, 0)), it.extras.getString(JOB_REMINDER_ID, "0").toInt())
        }
    }

    private fun prepareNotification(taskTitle: String, minutes: Int): Notification {
        val resultIntent = Intent(context, MainActivity::class.java)
        val builder = NotificationCompat.Builder(context, "")
        val notificationBody = if (minutes < 60)
            context.getString(R.string.reminder_notification_minutes_body_format, minutes) else context.getString(R.string.reminder_notification_hour_body)
        return builder
                .setContentText(notificationBody)
                .setContentTitle(taskTitle)
                .setCategory(android.app.Notification.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setDefaults(android.app.Notification.DEFAULT_SOUND or android.app.Notification.DEFAULT_VIBRATE)
                .setContentIntent(
                        PendingIntent.getActivity(context, 0, resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .build()
    }

    private fun showNotification(notification: Notification, id: Int) {
        // Gets an instance of the NotificationManager service
        val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // Builds the notification and issues it.
        manager.notify(id, notification)
    }

    private fun removeReminder(reminder: TaskReminder?) {
        reminder?.let {
            val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.cancel(it.taskId.toInt())
            reminders.remove(reminder)
        }
    }

    private fun registerReminder(task: Task, minutes: Int): Boolean {
        val taskId = task.id ?: return false
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val serviceName = ComponentName(context, ReminderService::class.java)
        val bundle = PersistableBundle()
        bundle.putString(JOB_REMINDER_ID, taskId)
        bundle.putString(JOB_REMINDER_TITLE, task.title)
        bundle.putInt(JOB_REMINDER_MINUTES, minutes)
        val jobInfo = JobInfo.Builder(taskId.toInt(), serviceName)
                .setExtras(bundle)
                .setPersisted(true)
                .setMinimumLatency(task.dueBy.toLong() - Calendar.getInstance().timeInMillis - TimeUnit.MINUTES.toMillis(minutes.toLong()))
                .setOverrideDeadline(task.dueBy.toLong() - Calendar.getInstance().timeInMillis - TimeUnit.MINUTES.toMillis(minutes.toLong()) + 1000)
                .build()
        val result = jobScheduler.schedule(jobInfo)
        return result == JobScheduler.RESULT_SUCCESS
    }

    private fun saveReminders() {
        PreferencesUtil.putString(STORED_REMINDERS, gson.toJson(reminders))
    }

    data class TaskReminder(
            @SerializedName("taskId") val taskId: String,
            @SerializedName("taskTitle") val taskTitle: String,
            @SerializedName("taskDueTo") val taskDueTo: Long,
            @SerializedName("remindValue") val remindValue: Int
    )
}