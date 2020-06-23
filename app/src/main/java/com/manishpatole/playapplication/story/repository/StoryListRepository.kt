package com.manishpatole.playapplication.story.repository

import com.manishpatole.playapplication.story.service.StoryService
import com.manishpatole.playapplication.utils.Result
import javax.inject.Inject

class StoryListRepository @Inject constructor(
    private val storyService: StoryService
) {
    suspend fun getStoryList(): Result<List<Int>?> {
        val response = storyService.getStoryList()
        return if (response.isSuccessful) {
            Result.success(response.body())
        } else {
            Result.error(null)
        }
    }
}