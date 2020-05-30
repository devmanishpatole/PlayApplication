package com.manishpatole.playapplication.login.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.manishpatole.playapplication.base.BaseViewModel
import com.manishpatole.playapplication.login.model.LoginRequest
import com.manishpatole.playapplication.login.repository.LoginRepository
import com.manishpatole.playapplication.utils.NetworkHelper
import com.manishpatole.playapplication.utils.Result
import com.manishpatole.playapplication.utils.validateEmail
import com.manishpatole.playapplication.utils.validatePassword
import kotlinx.coroutines.launch


class LoginViewModel(
    networkHelper: NetworkHelper,
    private val loginRepository: LoginRepository
) :
    BaseViewModel(networkHelper) {

    private val _loginResponse = MutableLiveData<Result<*>>()
    val loginResponse: LiveData<Result<*>>
        get() = _loginResponse

    private val _emailValidation = MutableLiveData<Boolean>()
    val emailValidation: LiveData<Boolean>
        get() = _emailValidation

    private val _passwordValidation = MutableLiveData<Boolean>()
    val passwordValidation: LiveData<Boolean>
        get() = _passwordValidation

    fun login(username: String, password: String) {
        if (validateEmailAndPassword(username, password)) {
            performLogin(
                LoginRequest(
                    username,
                    password
                )
            )
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun validateEmailAndPassword(username: String, password: String): Boolean {
        var result = true

        if (!validateEmail(username)) {
            _emailValidation.value = false
            result = false
        }
        if (!validatePassword(password)) {
            _passwordValidation.value = false
            result = false
        }

        return result
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun performLogin(loginRequest: LoginRequest) {
        if (checkInternetConnection()) {
            _loginResponse.value = Result.loading(null)
            viewModelScope.launch {
                loginRepository.login(loginRequest,
                    {
                        _loginResponse.postValue(Result.success(it?.token))
                    },
                    {
                        _loginResponse.postValue(Result.error(it))
                    })
            }
        } else {
            _loginResponse.value = (Result.noInternetError(null))
        }
    }
}