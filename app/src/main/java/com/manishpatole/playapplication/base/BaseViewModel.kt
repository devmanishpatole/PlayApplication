package com.manishpatole.playapplication.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manishpatole.playapplication.utils.NetworkHelper
import com.manishpatole.playapplication.utils.Result


abstract class BaseViewModel(
    protected val networkHelper: NetworkHelper
) : ViewModel() {

    private val _messageStringId: MutableLiveData<Result<Int>> = MutableLiveData()
    val messageStringId: LiveData<Result<Int>>
        get() = _messageStringId

    private val _messageString: MutableLiveData<Result<String>> = MutableLiveData()
    val messageString: LiveData<Result<String>>
        get() = _messageString

    protected fun checkInternetConnection() = networkHelper.isNetworkConnected()

}