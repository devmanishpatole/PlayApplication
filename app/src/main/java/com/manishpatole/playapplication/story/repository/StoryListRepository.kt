package com.manishpatole.playapplication.story.repository

import com.manishpatole.playapplication.story.service.StoryService
import com.manishpatole.playapplication.utils.Result
import javax.inject.Inject

class StoryListRepository @Inject constructor(
    private val storyService: StoryService
) {
    suspend fun getStoryList(): Result<List<Int>?> {
        return try {
            val response = storyService.getStoryList()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.error(null)
            }
        } catch (e: Exception) {
            Result.error(null)
        }
    }
}