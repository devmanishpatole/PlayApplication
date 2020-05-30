package com.manishpatole.playapplication.utils

import android.content.Context

class MockNetworkHelper(context: Context) : NetworkHelper(context) {
    var isConnected = false

    override
    fun isNetworkConnected(): Boolean {
        return isConnected
    }
}