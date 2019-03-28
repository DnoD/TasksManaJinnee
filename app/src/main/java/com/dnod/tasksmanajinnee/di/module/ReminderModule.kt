package com.dnod.tasksmanajinnee.di.module

import com.dnod.tasksmanajinnee.manager.ReminderManager
import com.dnod.tasksmanajinnee.manager.ReminderManagerImpl
import com.dnod.tasksmanajinnee.service.ReminderService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class ReminderModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        internal fun provideReminderManagerInstance(manager: ReminderManagerImpl): ReminderManager {
            return manager
        }

        @JvmStatic
        @Provides
        @Singleton
        internal fun provideReminderJobHandler(handler: ReminderManagerImpl): ReminderService.JobHandler {
            return handler
        }
    }

}
