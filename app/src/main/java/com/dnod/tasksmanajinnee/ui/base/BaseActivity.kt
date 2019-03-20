package com.dnod.tasksmanajinnee.ui.base

import android.os.Bundle
import android.view.View
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    protected abstract val rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
