package com.manishpatole.playapplication.di.component

import android.content.SharedPreferences
import com.manishpatole.playapplication.application.PlayApplication
import com.manishpatole.playapplication.di.module.ApplicationModule
import com.manishpatole.playapplication.login.service.LoginService
import com.manishpatole.playapplication.story.service.StoryService
import com.manishpatole.playapplication.theme.ThemeManager
import com.manishpatole.playapplication.utils.NetworkHelper
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: PlayApplication)

    fun getNetworkHelper(): NetworkHelper

    fun getLoginService(): LoginService

    fun getSharedPreferences(): SharedPreferences

    fun getThemeManager(): ThemeManager

    fun getStoryService(): StoryService
}