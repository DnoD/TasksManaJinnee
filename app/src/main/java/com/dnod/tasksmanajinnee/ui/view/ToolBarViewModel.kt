package com.dnod.tasksmanajinnee.ui.view

import android.databinding.ObservableField
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.dnod.tasksmanajinnee.ui.base.BaseViewModel
import com.dnod.tasksmanajinnee.ui.getDrawable
import com.dnod.tasksmanajinnee.ui.getString

class ToolBarViewModel(
        @StringRes titleRes: Int,
        @DrawableRes leftIconRes: Int?,
        @DrawableRes rightIconRes: Int?,
        private val listener: Listener
) : BaseViewModel() {

    val title = ObservableField<String>()
    val leftIcon = ObservableField<Drawable>()
    val rightIcon = ObservableField<Drawable>()

    init {
        title.set(getString(titleRes))
        leftIconRes?.let {
            leftIcon.set(getDrawable(it))
        }
        rightIconRes?.let {
            rightIcon.set(getDrawable(it))
        }
    }

    fun leftAction() {
        listener.onLeftAction()
    }

    fun rightAction() {
        listener.onRightAction()
    }

    interface Listener {
        fun onLeftAction()

        fun onRightAction()
    }
}