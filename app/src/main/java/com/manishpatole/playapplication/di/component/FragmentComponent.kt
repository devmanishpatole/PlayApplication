package com.manishpatole.playapplication.di.component

import com.manishpatole.playapplication.di.FragmentScope
import com.manishpatole.playapplication.di.module.FragmentModule
import com.manishpatole.playapplication.story.ui.StoryDetailFragment
import com.manishpatole.playapplication.story.ui.StoryListFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {

    fun inject(fragment: StoryListFragment)

    fun inject(fragment: StoryDetailFragment)


}