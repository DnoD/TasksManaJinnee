package com.dnod.tasksmanajinnee.ui

import android.graphics.drawable.Drawable
import android.support.annotation.ArrayRes
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import com.dnod.tasksmanajinnee.ManaJinneeApplication
import com.dnod.tasksmanajinnee.ui.base.BaseViewModel

fun BaseViewModel.getStringArray(@ArrayRes arrayRes: Int): Array<String> {
    return ManaJinneeApplication.instance.resources.getStringArray(arrayRes)
}

fun BaseViewModel.getIntArray(@ArrayRes arrayRes: Int): IntArray {
    return ManaJinneeApplication.instance.resources.getIntArray(arrayRes)
}

fun BaseViewModel.getString(@StringRes stringRes: Int): String {
    return ManaJinneeApplication.instance.getString(stringRes)
}

fun BaseViewModel.getString(@StringRes stringRes: Int, vararg args: String): String {
    return ManaJinneeApplication.instance.getString(stringRes, *args)
}

fun BaseViewModel.getColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(ManaJinneeApplication.instance, colorRes)
}

fun BaseViewModel.getDrawable(@DrawableRes drawableRes: Int): Drawable? {
    return ContextCompat.getDrawable(ManaJinneeApplication.instance, drawableRes)
}
