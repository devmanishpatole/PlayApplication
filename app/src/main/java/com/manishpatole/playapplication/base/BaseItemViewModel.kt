package com.manishpatole.playapplication.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.manishpatole.playapplication.utils.NetworkHelper

abstract class BaseItemViewModel<T>(networkHelper: NetworkHelper) : BaseViewModel(networkHelper) {

    private val _data = MutableLiveData<T>()
    val data: LiveData<T>
        get() = _data


    open fun updateData(data: T) {
        _data.postValue(data)
    }

    fun onManualClear() = onCleared()
}