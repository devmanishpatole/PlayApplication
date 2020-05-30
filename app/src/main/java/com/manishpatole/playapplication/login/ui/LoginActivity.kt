package com.manishpatole.playapplication.login.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.manishpatole.playapplication.R
import com.manishpatole.playapplication.base.BaseActivity
import com.manishpatole.playapplication.di.component.ActivityComponent
import com.manishpatole.playapplication.login.viewmodel.LoginViewModel
import com.manishpatole.playapplication.story.ui.StoryActivity
import com.manishpatole.playapplication.utils.Status
import kotlinx.android.synthetic.main.activity_login.*
import javax.net.ssl.HttpsURLConnection


class LoginActivity : BaseActivity<LoginViewModel>() {

    override fun getLayoutId() = R.layout.activity_login

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        switchTheme.isChecked = themeManager.isDarkTheme()
        initObservers()
        initListeners()
    }

    private fun initListeners() {
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            themeManager.changeTheme(isChecked) {
                recreate()
            }
        }

        loginButton.setOnClickListener {
            hideKeyboard()
            viewModel.login(editUsername.text.toString(), editPassword.text.toString())
        }
    }

    private fun initObservers() {
        editUsername.addTextChangedListener {
            usernameWrapper.error = null
        }
        editPassword.addTextChangedListener {
            passwordWrapper.error = null
        }

        viewModel.emailValidation.observe(this, Observer { valid ->
            if (!valid) {
                usernameWrapper.error = getString(R.string.enter_valid_email)
            } else {
                usernameWrapper.error = null
            }
        })

        viewModel.passwordValidation.observe(this, Observer { valid ->
            if (!valid) {
                passwordWrapper.error = getString(R.string.enter_valid_password)
            } else {
                passwordWrapper.error = null
            }
        })

        viewModel.loginResponse.observe(this, Observer {
            var errorDescription = getString(R.string.something_went_wrong)
            when (it.status) {
                Status.SUCCESS -> {
                    startActivity(Intent(this, StoryActivity::class.java))
                    finish()
                }
                Status.ERROR -> {
                    hideProgressbar()
                    it.data?.let { message ->
                        when (message as Int) {
                            HttpsURLConnection.HTTP_UNAUTHORIZED -> {
                                errorDescription = getString(R.string.unauthorised)
                            }
                            HttpsURLConnection.HTTP_BAD_REQUEST -> {
                                errorDescription = getString(R.string.bad_request)
                            }
                        }
                        showMessage(errorDescription)
                    }
                }
                Status.LOADING -> showProgressbarWithText(getString(R.string.loading))
                Status.NO_INTERNET -> showMessage(getString(R.string.no_internet))
                else -> showMessage(errorDescription)
            }
        })

    }

}
