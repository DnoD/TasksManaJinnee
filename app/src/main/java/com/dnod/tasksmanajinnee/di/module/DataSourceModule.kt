package com.dnod.tasksmanajinnee.di.module

import com.dnod.tasksmanajinnee.data.source.TasksDataSource
import com.dnod.tasksmanajinnee.data.source.TasksRepository
import com.dnod.tasksmanajinnee.data.source.local.LocalTasks
import com.dnod.tasksmanajinnee.data.source.local.TasksLocalDataSource
import com.dnod.tasksmanajinnee.data.source.remote.RemoteTasks
import com.dnod.tasksmanajinnee.data.source.remote.TasksRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DataSourceModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        internal fun provideTasksRepository(dataSource: TasksRepository): TasksDataSource {
            return dataSource
        }

        @JvmStatic
        @Provides
        @Singleton
        @RemoteTasks
        internal fun provideRemoteTasksDataSource(dataSource: TasksRemoteDataSource): TasksDataSource {
            return dataSource
        }

        @JvmStatic
        @Provides
        @Singleton
        @LocalTasks
        internal fun provideLocalTasksDataSource(dataSource: TasksLocalDataSource): TasksDataSource {
            return dataSource
        }
    }
}
