package com.dnod.tasksmanajinnee.ui

import android.support.annotation.StringRes
import com.dnod.tasksmanajinnee.ManaJinneeApplication
import com.dnod.tasksmanajinnee.ui.base.BaseViewModel

fun BaseViewModel.getString(@StringRes stringRes: Int): String {
    return ManaJinneeApplication.instance.getString(stringRes)
}

fun BaseViewModel.getString(@StringRes stringRes: Int, vararg args: String): String {
    return ManaJinneeApplication.instance.getString(stringRes, *args)
}