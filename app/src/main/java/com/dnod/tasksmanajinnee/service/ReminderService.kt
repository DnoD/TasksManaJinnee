package com.dnod.tasksmanajinnee.service

import android.app.job.JobParameters
import android.app.job.JobService
import com.dnod.tasksmanajinnee.ManaJinneeApplication
import javax.inject.Inject

class ReminderService : JobService() {

    @Inject
    lateinit var jobHandler: JobHandler

    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        ManaJinneeApplication.appComponent?.inject(this)
        jobHandler.handleJob(params)
        return false
    }

    interface JobHandler {

        fun handleJob(params: JobParameters?)
    }
}