package com.manishpatole.playapplication.story.viewmodel

import com.manishpatole.playapplication.base.BaseViewModel
import com.manishpatole.playapplication.utils.NetworkHelper
import javax.inject.Inject

class DisplayStoryDetailViewModel @Inject constructor(networkHelper: NetworkHelper) :
    BaseViewModel(networkHelper) {
    //Need ViewModel in case want to perform any operation with story detail.
}