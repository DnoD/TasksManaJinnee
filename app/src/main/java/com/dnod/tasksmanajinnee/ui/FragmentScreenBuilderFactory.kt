package com.dnod.tasksmanajinnee.ui

import com.dnod.tasksmanajinnee.ui.auth.LoginFragment
import com.dnod.tasksmanajinnee.ui.base.BaseFragment
import com.dnod.tasksmanajinnee.ui.base.FragmentBuilder
import javax.inject.Inject

/**
 * This is the Factory which is dedicated in create the Concrete Builder of the Screen which is going to be attached using Fragments
 */
class FragmentScreenBuilderFactory @Inject constructor() : ScreenBuilderFactory<BaseFragment> {

    override fun create(screen: BaseFragment): Conductor.ScreenBuilder<BaseFragment> {
        return when (screen) {
            is LoginFragment -> FragmentBuilder(screen).setRootScreen()
            else -> throw UnsupportedOperationException()
        }
    }

}