package com.dnod.tasksmanajinnee.di.component

import android.content.Context
import com.dnod.tasksmanajinnee.di.module.ActivityBindingModule
import com.dnod.tasksmanajinnee.di.module.DataSourceModule
import com.dnod.tasksmanajinnee.di.module.NetworkModule
import com.dnod.tasksmanajinnee.di.module.SortingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    DataSourceModule::class,
    SortingModule::class,
    NetworkModule::class
])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    override fun inject(instance: DaggerApplication)

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(context: Context): Builder

        fun build(): ApplicationComponent
    }

}
