package com.dnod.tasksmanajinnee.di.module

import com.dnod.tasksmanajinnee.di.scope.ActivityScoped
import com.dnod.tasksmanajinnee.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun mainActivity(): MainActivity
}
