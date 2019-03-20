package com.dnod.tasksmanajinnee.ui.base

import com.dnod.tasksmanajinnee.ui.Conductor

class FragmentBuilder(
    private val fragment: BaseFragment
) : Conductor.ScreenBuilder<BaseFragment> {

    private var isRootScreen: Boolean = false

    fun setRootScreen() : FragmentBuilder {
        this.isRootScreen = true
        return this
    }

    override fun getScreen(): BaseFragment {
        return fragment
    }

    override fun isRoot(): Boolean {
        return isRootScreen
    }
}
