package com.dnod.tasksmanajinnee.ui.auth

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.databinding.FragmentLoginBinding
import com.dnod.tasksmanajinnee.manager.AuthManager
import com.dnod.tasksmanajinnee.ui.Conductor
import com.dnod.tasksmanajinnee.ui.ScreenBuilderFactory
import com.dnod.tasksmanajinnee.ui.base.BaseFragment
import javax.inject.Inject

class LoginFragment @Inject constructor() : BaseFragment() {

    private lateinit var viewDataBinding: FragmentLoginBinding
    private val authSucceedObserver = Observer<Void> {
        showMessage(R.string.message_under_construction)
    }

    @Inject
    lateinit var conductor: Conductor<Conductor.ScreenBuilder<BaseFragment>>

    @Inject
    lateinit var screenBuilderFactory: ScreenBuilderFactory<BaseFragment>

    @Inject
    lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        viewDataBinding.apply {
            viewModel = ViewModelProviders.of(this@LoginFragment).get(LoginViewModel::class.java)
            val nonOptionalViewModel = viewModel?.let { it } ?: return@apply
            nonOptionalViewModel.start(authManager)
        }
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        val viewModel = viewDataBinding.viewModel?.let { it } ?: return
        viewModel.authSucceedComand.observe(this, authSucceedObserver)
    }

    override fun onPause() {
        super.onPause()
        val viewModel = viewDataBinding.viewModel?.let { it } ?: return
        viewModel.authSucceedComand.removeObserver(authSucceedObserver)
    }

    override fun getScreenTag(): String {
        return TAG
    }

    companion object {
        private val TAG = LoginFragment::class.java.simpleName

        fun createInstance(): LoginFragment {
            val fragment = LoginFragment()
            val arguments = Bundle()
            fragment.arguments = arguments
            return fragment
        }
    }
}
