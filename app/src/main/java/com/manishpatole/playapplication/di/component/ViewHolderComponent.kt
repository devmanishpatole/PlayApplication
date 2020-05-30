package com.manishpatole.playapplication.di.component

import com.manishpatole.playapplication.di.ViewHolderScope
import com.manishpatole.playapplication.di.module.ViewHolderModule
import com.manishpatole.playapplication.story.viewholder.StoryViewHolder
import dagger.Component

@ViewHolderScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ViewHolderModule::class]
)
interface ViewHolderComponent {

    fun inject(holder : StoryViewHolder)

}