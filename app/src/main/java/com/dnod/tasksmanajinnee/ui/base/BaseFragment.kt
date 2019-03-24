package com.dnod.tasksmanajinnee.ui.base

import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.View
import com.dnod.tasksmanajinnee.ui.BackInterceptor
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment(), BackInterceptor {

    protected abstract val rootView: View

    fun showMessage(message: Int) {
        showMessage(getString(message))
    }

    fun showMessage(message: String) {
        makeSnackBar(message, Snackbar.LENGTH_LONG).show()
    }

    protected fun makeSnackBar(@StringRes message: Int, duration: Int): Snackbar {
        return makeSnackBar(getString(message), duration)
    }

    protected fun makeSnackBar(message: String, duration: Int): Snackbar {
        return Snackbar.make(rootView, message, duration)
    }

    protected fun makeRootSnackBar(@StringRes message: Int, duration: Int): Snackbar {
        return makeRootSnackBar(getString(message), duration)
    }

    protected fun makeRootSnackBar(message: String, duration: Int): Snackbar {
        return (activity as BaseActivity).makeSnackBar(message, duration)
    }

    override fun handleBackPress(): Boolean {
        return false
    }

    abstract fun getScreenTag(): String
}
