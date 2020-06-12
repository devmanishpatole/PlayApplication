package com.manishpatole.playapplication.story.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.manishpatole.playapplication.base.BaseViewModel
import com.manishpatole.playapplication.story.model.StoryRequestWrapper
import com.manishpatole.playapplication.story.repository.StoryListRepository
import com.manishpatole.playapplication.utils.NetworkHelper
import com.manishpatole.playapplication.utils.Result
import kotlinx.coroutines.launch

class StoryListViewModel(
    networkHelper: NetworkHelper,
    private val storyListRepository: StoryListRepository
) : BaseViewModel(networkHelper) {

    private var storyList: List<Int>? = null

    private val _stories = MutableLiveData<Result<List<Int>?>>()
    val stories: LiveData<Result<List<Int>?>>
        get() = _stories

    var numberList: ArrayList<StoryRequestWrapper>? = null

    fun loadStories() {
        if (storyList?.isNotEmpty() == true) {
            _stories.value = Result.success(storyList)
        } else {
            if (networkHelper.isNetworkConnected()) {
                viewModelScope.launch {
                    _stories.value = Result.loading(null)
                    val result = storyListRepository.getStoryList()
                    result.data?.let {
                        storyList = it
                    }
                    _stories.value = result
                }
            } else {
                _stories.value = Result.noInternetError(null)
            }
        }
    }

}