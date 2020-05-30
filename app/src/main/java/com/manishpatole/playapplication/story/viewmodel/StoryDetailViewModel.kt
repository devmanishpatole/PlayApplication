package com.manishpatole.playapplication.story.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.manishpatole.playapplication.base.BaseItemViewModel
import com.manishpatole.playapplication.story.model.StoryRequestWrapper
import com.manishpatole.playapplication.story.repository.StoryDetailRepository
import com.manishpatole.playapplication.utils.NetworkHelper
import com.manishpatole.playapplication.utils.Result
import com.manishpatole.playapplication.utils.Status
import kotlinx.coroutines.launch
import javax.inject.Inject

open class StoryDetailViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    private val repository: StoryDetailRepository
) : BaseItemViewModel<StoryRequestWrapper>(
    networkHelper
) {
    private val _listResponseObserver = MutableLiveData<Result<*>>()
    val storyRequestWrapperObserver: LiveData<Result<*>>
        get() = _listResponseObserver

    fun getStoryDetail(data: StoryRequestWrapper) {
        viewModelScope.launch {
            if (checkInternetConnection()) {
                val result = repository.getStoryDetail(data.number)

                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let {
                            data.topStory = it
                            _listResponseObserver.value = Result.success(data)
                        }
                    }
                    else -> {
                        _listResponseObserver.value = Result.error(data)
                    }
                }
            } else {
                _listResponseObserver.value = Result.noInternetError(null)
            }
        }
    }
}