package com.dnod.tasksmanajinnee.ui.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.databinding.ActivityMainBinding
import com.dnod.tasksmanajinnee.ui.Conductor
import com.dnod.tasksmanajinnee.ui.ScreenBuilderFactory
import com.dnod.tasksmanajinnee.ui.base.BaseActivity
import com.dnod.tasksmanajinnee.ui.base.BaseFragment
import javax.inject.Inject

class MainActivity : BaseActivity() , Conductor<Conductor.ScreenBuilder<BaseFragment>> {

    private lateinit var bindingObject: ActivityMainBinding

    override val rootView: View
        get() = bindingObject.root

    private var currFragmentBuilder: Conductor.ScreenBuilder<BaseFragment>? = null

    @Inject
    lateinit var screenBuilderFactory: ScreenBuilderFactory<BaseFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindingObject = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun goTo(builder: Conductor.ScreenBuilder<BaseFragment>) {
        val screen = builder.getScreen()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (builder.isRoot()) {
            //Clean back stack if any
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            fragmentTransaction.addToBackStack(screen.getScreenTag())
        }
        currFragmentBuilder = builder
        fragmentTransaction.replace(bindingObject.container.id, screen, screen.getScreenTag())
                .commit()
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun onBackPressed() {
        if (currFragmentBuilder?.getScreen()?.handleBackPress() == true) {
            return
        }
        val prevIndex = supportFragmentManager.backStackEntryCount
        super.onBackPressed()
        if (prevIndex == 0) {
            return
        }
        postBackSetup()
    }

    private fun postBackSetup() {
        val stackFragment = supportFragmentManager.findFragmentById(bindingObject.container.getId()) as BaseFragment
        val stackBuilder = screenBuilderFactory.create(stackFragment)
        currFragmentBuilder = stackBuilder
    }
}
