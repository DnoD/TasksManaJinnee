package com.dnod.tasksmanajinnee.ui

import com.dnod.tasksmanajinnee.ui.base.BaseFragment
import javax.inject.Inject

/**
 * This is the Factory which is dedicated in create the Concrete Builder of the Screen which is going to be attached using Fragments
 */
class FragmentScreenBuilderFactory @Inject constructor() : ScreenBuilderFactory<BaseFragment> {

    override fun create(screen: BaseFragment): Conductor.ScreenBuilder<BaseFragment> {
        return when (screen) {
            else -> throw UnsupportedOperationException()
        }
    }

}