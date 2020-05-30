package com.manishpatole.playapplication.di.module

import androidx.lifecycle.LifecycleRegistry
import com.manishpatole.playapplication.base.BaseItemViewHolder
import com.manishpatole.playapplication.di.ViewHolderScope
import dagger.Module
import dagger.Provides

@Module
class ViewHolderModule(private val viewHolder: BaseItemViewHolder<*, *>) {

    @Provides
    @ViewHolderScope
    fun provideLifecycleRegistry(): LifecycleRegistry = LifecycleRegistry(viewHolder)
}