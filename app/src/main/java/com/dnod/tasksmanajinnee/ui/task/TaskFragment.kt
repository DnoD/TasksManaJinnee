package com.dnod.tasksmanajinnee.ui.task

import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.data.source.TasksDataSource
import com.dnod.tasksmanajinnee.databinding.FragmentTaskBinding
import com.dnod.tasksmanajinnee.ui.Conductor
import com.dnod.tasksmanajinnee.ui.base.BaseFragment
import java.util.*

import javax.inject.Inject

class TaskFragment : BaseFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        private val TAG = TaskFragment::class.java.simpleName
        private val PROVIDED_TASK_ID = "provided_task_id"

        fun createInstance(): BaseFragment {
            return createInstance(null)
        }

        fun createInstance(taskId: String?): BaseFragment {
            val fragment = TaskFragment()
            val arguments = Bundle()
            if (taskId != null) {
                arguments.putString(PROVIDED_TASK_ID, taskId)
            }
            fragment.arguments = arguments
            return fragment
        }
    }

    private val errorObserver = Observer<String> { error ->
        error?.let {
            showMessage(it)
        }
    }

    private val saveEventObserver = Observer<Void> {
        if (arguments?.containsKey(PROVIDED_TASK_ID) == true) {
            showRootMessage(R.string.task_create_update_screen_message_create_succeed)
        } else {
            showRootMessage(R.string.task_create_update_screen_message_create_succeed)
        }
        conductor.goBack()
    }

    private lateinit var viewDataBinding: FragmentTaskBinding

    @Inject
    lateinit var conductor: Conductor<Conductor.ScreenBuilder<BaseFragment>>

    @Inject
    lateinit var tasksDataSource: TasksDataSource

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task, container, false)
        viewDataBinding.apply {
            viewModel = ViewModelProviders.of(this@TaskFragment).get(TaskViewModel::class.java)
            val nonOptionalViewModel = viewModel?.let { it } ?: return@apply
            nonOptionalViewModel.backAction.observe(this@TaskFragment, Observer {
                conductor.goBack()
            })
            nonOptionalViewModel.pickDueToAction.observe(this@TaskFragment, Observer { dueTo ->
                dueTo?.let {
                    showDueToPicker(it)
                }
            })
            nonOptionalViewModel.start(arguments?.getString(PROVIDED_TASK_ID)
                    ?: "", tasksDataSource)
        }
        return viewDataBinding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        viewDataBinding.viewModel?.setDueTo(calendar)
    }

    override fun onResume() {
        super.onResume()
        val viewModel = viewDataBinding.viewModel?.let { it } ?: return
        viewModel.errorEvent.observe(this, errorObserver)
        viewModel.saveSucceedEvent.observe(this, saveEventObserver)
    }

    override fun onPause() {
        super.onPause()
        val viewModel = viewDataBinding.viewModel?.let { it } ?: return
        viewModel.errorEvent.removeObserver(errorObserver)
        viewModel.saveSucceedEvent.removeObserver(saveEventObserver)
    }

    override val rootView: View
        get() = viewDataBinding.detailsRoot

    override fun getScreenTag(): String {
        return TAG
    }

    private fun showDueToPicker(dueto: Long) {
        val calendar = Calendar.getInstance()
        if (dueto != 0L) {
            calendar.timeInMillis = dueto
        }
        val datePickerDialog = DatePickerDialog(
                context, this@TaskFragment,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
        val now = Calendar.getInstance()
        now.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) + 1)
        datePickerDialog.datePicker.minDate = now.timeInMillis
        datePickerDialog.show()
    }
}