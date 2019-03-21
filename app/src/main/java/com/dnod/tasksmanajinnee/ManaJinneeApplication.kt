package com.dnod.tasksmanajinnee

import com.dnod.tasksmanajinnee.di.component.ApplicationComponent
import com.dnod.tasksmanajinnee.di.component.DaggerApplicationComponent
import com.dnod.tasksmanajinnee.utils.PreferencesUtil
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class ManaJinneeApplication  : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        PreferencesUtil.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        appComponent = DaggerApplicationComponent.builder().application(this).build()
        appComponent?.inject(this)
        return appComponent
    }

    companion object {

        lateinit var instance: ManaJinneeApplication

        var appComponent: ApplicationComponent? = null
            get
    }

}