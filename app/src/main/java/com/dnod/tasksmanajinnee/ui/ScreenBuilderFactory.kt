package com.dnod.tasksmanajinnee.ui

interface ScreenBuilderFactory<Screen> {

    fun create(screen: Screen): Conductor.ScreenBuilder<Screen>
}
