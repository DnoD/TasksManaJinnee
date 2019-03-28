package com.dnod.tasksmanajinnee.ui

import com.dnod.tasksmanajinnee.ui.auth.LoginFragment
import com.dnod.tasksmanajinnee.ui.base.BaseFragment
import com.dnod.tasksmanajinnee.ui.base.FragmentBuilder
import com.dnod.tasksmanajinnee.ui.reminder.RemindersFragment
import com.dnod.tasksmanajinnee.ui.task.TaskFragment
import com.dnod.tasksmanajinnee.ui.taskdetails.TaskDetailsFragment
import com.dnod.tasksmanajinnee.ui.tasks.TasksFragment
import javax.inject.Inject

/**
 * This is the Factory which is dedicated in create the Concrete Builder of the Screen which is going to be attached using Fragments
 */
class FragmentScreenBuilderFactory @Inject constructor() : ScreenBuilderFactory<BaseFragment> {

    override fun create(screen: BaseFragment): Conductor.ScreenBuilder<BaseFragment> {
        return when (screen) {
            is LoginFragment -> FragmentBuilder(screen).setRootScreen()
            is TasksFragment -> FragmentBuilder(screen).setRootScreen()
            is TaskDetailsFragment -> FragmentBuilder(screen)
            is RemindersFragment -> FragmentBuilder(screen)
            is TaskFragment -> FragmentBuilder(screen)
            else -> throw UnsupportedOperationException()
        }
    }

}