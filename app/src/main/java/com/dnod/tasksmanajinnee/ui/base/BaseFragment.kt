package com.dnod.tasksmanajinnee.ui.base

import com.dnod.tasksmanajinnee.ui.BackInterceptor
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment(), BackInterceptor {

    override fun handleBackPress(): Boolean {
        return false
    }

    abstract fun getScreenTag(): String
}
