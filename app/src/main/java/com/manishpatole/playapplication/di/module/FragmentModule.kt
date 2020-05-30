package com.manishpatole.playapplication.di.module

import androidx.lifecycle.ViewModelProvider
import com.manishpatole.playapplication.base.BaseFragment
import com.manishpatole.playapplication.base.ViewModelProviderFactory
import com.manishpatole.playapplication.story.adapter.StoryListAdapter
import com.manishpatole.playapplication.story.repository.StoryListRepository
import com.manishpatole.playapplication.story.viewmodel.StoryListViewModel
import com.manishpatole.playapplication.utils.NetworkHelper
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    fun provideStoryListViewModel(
        networkHelper: NetworkHelper,
        storyListRepository: StoryListRepository
    ): StoryListViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(StoryListViewModel::class) {
            StoryListViewModel(networkHelper, storyListRepository)
        }).get(StoryListViewModel::class.java)


    @Provides
    fun provideStoryAdapter() = StoryListAdapter(fragment.lifecycle, ArrayList())
}