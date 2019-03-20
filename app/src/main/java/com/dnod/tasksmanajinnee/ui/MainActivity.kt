package com.dnod.tasksmanajinnee.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.databinding.ActivityMainBinding
import com.dnod.tasksmanajinnee.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var bindingObject: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingObject = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override val rootView: View
        get() = bindingObject.root

}
