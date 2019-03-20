package com.dnod.tasksmanajinnee.ui

interface Conductor<ScreenBuilder> {

    interface ScreenBuilder<Screen> {

        fun getScreen(): Screen

        fun isRoot(): Boolean
    }

    fun goTo(builder: ScreenBuilder)

    fun goBack()
}