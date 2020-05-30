package com.manishpatole.playapplication.story.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manishpatole.playapplication.story.model.TopStory

class SharedViewModel : ViewModel() {

    private val _storyDetailLauncher = MutableLiveData<TopStory>()
    val storyDetailLauncher: LiveData<TopStory>
        get() = _storyDetailLauncher

    fun launchDetail(topStory: TopStory) {
        _storyDetailLauncher.postValue(topStory)
    }
}