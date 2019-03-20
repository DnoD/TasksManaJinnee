package com.dnod.tasksmanajinnee.ui.auth

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import com.dnod.tasksmanajinnee.R
import com.dnod.tasksmanajinnee.ui.SingleEvent
import com.dnod.tasksmanajinnee.ui.base.BaseViewModel
import com.dnod.tasksmanajinnee.ui.getString

class LoginViewModel : BaseViewModel() {

    private var userName: String = ""
    private var password: String = ""
    private var passwordConfirm: String = ""

    internal val authSucceedComand = SingleEvent<Void>()

    val authFailureMessage = ObservableField<String>()
    val btnAuthText = ObservableField<String>()
    val isAuthEnabled = ObservableBoolean(false)
    val isDataLoading = ObservableBoolean(false)
    val isRegistrationEnabled = ObservableBoolean(false)

    fun start() {
        updateAuthError("")
        updateBtnAuthText()
    }

    fun auth() {
        if (isRegistrationEnabled.get() && passwordConfirm != password) {
            updateAuthError(getString(R.string.login_screen_error_password_not_matches))
            return
        }
        isDataLoading.set(true)
    }

    fun onUsernameChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        //Reset Auth error when user start to change the login form values
        updateAuthError("")
        userName = s.toString()
        updateAuthButtonState()
    }

    fun onPasswordChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        //Reset Auth error when user start to change the login form values
        updateAuthError("")
        password = s.toString()
        updateAuthButtonState()
    }

    fun onPasswordConfirmChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        //Reset Auth error when user start to change the login form values
        updateAuthError("")
        passwordConfirm = s.toString()
        updateAuthButtonState()
    }

    fun onAuthOptionChanged(view: View, checked: Boolean) {
        isRegistrationEnabled.set(checked)
        updateBtnAuthText()
        updateAuthButtonState()
        updateAuthError("")
    }

    fun onAuthSucceed() {
        isDataLoading.set(false)
        authSucceedComand.call()
    }

    fun onAuthFailure(errorMessage: String) {
        isDataLoading.set(false)
        updateAuthError(errorMessage)
    }

    private fun updateAuthButtonState() {
        isAuthEnabled.set(password.isNotBlank() && userName.isNotBlank() && (!isRegistrationEnabled.get() || passwordConfirm.isNotBlank()))
    }

    private fun updateAuthError(message: String) {
        this.authFailureMessage.set(message)
    }

    private fun updateBtnAuthText() {
        btnAuthText.set(getString(if (isRegistrationEnabled.get()) R.string.login_screen_btn_register else R.string.login_screen_btn_login))
    }
}