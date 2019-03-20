package com.dnod.tasksmanajinnee.ui.base

import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    abstract fun getScreenTag(): String
}
