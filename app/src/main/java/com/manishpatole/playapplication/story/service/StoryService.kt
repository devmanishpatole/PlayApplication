package com.manishpatole.playapplication.story.service

import com.manishpatole.playapplication.story.model.TopStory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StoryService {

    @GET("topstories.json")
    suspend fun getStoryList(): Response<List<Int>>

    @GET("item/{id}.json")
    suspend fun getStoryDetail(@Path("id") id: Int): Response<TopStory>

}