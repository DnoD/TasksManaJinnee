package com.dnod.tasksmanajinnee.ui.base

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.View
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    protected abstract val rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun makeSnackBar(@StringRes message: Int, duration: Int): Snackbar {
        return makeSnackBar(getString(message), duration)
    }

    fun makeSnackBar(message: String, duration: Int): Snackbar {
        return Snackbar.make(rootView, message, duration)
    }
}
