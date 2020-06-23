package com.manishpatole.playapplication.login.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.manishpatole.playapplication.BuildConfig
import com.manishpatole.playapplication.login.model.LoginRequest
import com.manishpatole.playapplication.login.model.LoginStatus
import com.manishpatole.playapplication.login.service.LoginService
import com.manishpatole.playapplication.utils.Constant.USER_INFO
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginService: LoginService,
    private val sharedPreferences: SharedPreferences
) {
    suspend fun login(loginRequest: LoginRequest): LoginStatus {
        val response = loginService.login(
            BuildConfig.LOGIN_URL,
            loginRequest
        )
        val status: LoginStatus
        status = if (response.isSuccessful) {
            sharedPreferences.edit { putBoolean(USER_INFO, true) }
            LoginStatus.Success(response.body())
        } else {
            LoginStatus.Failure(response.code())
        }
        return status
    }
}

