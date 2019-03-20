package com.dnod.tasksmanajinnee.di.module

import com.dnod.tasksmanajinnee.di.scope.ActivityScoped
import com.dnod.tasksmanajinnee.ui.Conductor
import com.dnod.tasksmanajinnee.ui.FragmentScreenBuilderFactory
import com.dnod.tasksmanajinnee.ui.ScreenBuilderFactory
import com.dnod.tasksmanajinnee.ui.base.BaseFragment
import com.dnod.tasksmanajinnee.ui.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
abstract class MainActivityModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @ActivityScoped
        internal fun provideScreenBuilderFactory(factory: FragmentScreenBuilderFactory): ScreenBuilderFactory<BaseFragment> {
            return factory
        }

        @JvmStatic
        @Provides
        @ActivityScoped
        internal fun provideConductor(activity: MainActivity): Conductor<Conductor.ScreenBuilder<BaseFragment>> {
            return activity
        }

    }

}
