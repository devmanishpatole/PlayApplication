package com.manishpatole.playapplication.story.repository

import com.manishpatole.playapplication.story.model.TopStory
import com.manishpatole.playapplication.story.service.StoryService
import com.manishpatole.playapplication.utils.Result
import javax.inject.Inject

class StoryDetailRepository @Inject constructor(
    private val storyService: StoryService
) {
    suspend fun getStoryDetail(id: Int): Result<TopStory?> {
        return try {
            val response = storyService.getStoryDetail(id)
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