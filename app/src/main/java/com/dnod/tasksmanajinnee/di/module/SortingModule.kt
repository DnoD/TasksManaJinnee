package com.dnod.tasksmanajinnee.di.module

import com.dnod.tasksmanajinnee.manager.SortingManager
import com.dnod.tasksmanajinnee.manager.SortingManagerImpl
import com.dnod.tasksmanajinnee.sorting.SortingProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class SortingModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        internal fun provideSortingManagerInstance(): SortingManagerImpl {
            return SortingManagerImpl()
        }

        @JvmStatic
        @Provides
        @Singleton
        internal fun provideSortingManager(manager: SortingManagerImpl): SortingManager {
            return manager
        }

        @JvmStatic
        @Provides
        @Singleton
        internal fun provideSortingProvider(manager: SortingManagerImpl): SortingProvider {
            return manager
        }

    }

}
