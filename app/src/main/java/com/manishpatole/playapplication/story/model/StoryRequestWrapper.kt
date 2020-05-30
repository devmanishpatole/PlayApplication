package com.manishpatole.playapplication.story.model

data class StoryRequestWrapper(val number: Int, var topStory: TopStory?) {
    val id = hashCode()
}