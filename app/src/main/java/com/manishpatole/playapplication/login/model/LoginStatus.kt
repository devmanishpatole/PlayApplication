package com.manishpatole.playapplication.login.model

import com.manishpatole.playapplication.R

sealed class LoginStatus {
    /**
     * Returns the data
     */
    class Success(val success: LoginSuccess?) : LoginStatus()

    /**
     * Returns the error code
     */
    class Failure(val error: Int, var errorMessageId: Int = R.string.something_went_wrong) :
        LoginStatus()
}