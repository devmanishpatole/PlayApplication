package com.manishpatole.playapplication.splash

import android.content.SharedPreferences
import androidx.core.content.edit
import com.manishpatole.playapplication.base.BaseViewModel
import com.manishpatole.playapplication.utils.Constant.USER_INFO
import com.manishpatole.playapplication.utils.NetworkHelper

/**
 * Handles login/logout status.
 */
class LoginManagerViewModel(
    networkHelper: NetworkHelper,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel(networkHelper) {

    fun isUserLoggedIn() = sharedPreferences.getBoolean(USER_INFO, false)

    fun logoutUser() = sharedPreferences.edit { putBoolean(USER_INFO, false) }
}