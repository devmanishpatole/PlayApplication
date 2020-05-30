package com.manishpatole.playapplication.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.manishpatole.playapplication.BuildConfig
import com.manishpatole.playapplication.application.PlayApplication
import com.manishpatole.playapplication.di.ApplicationContext
import com.manishpatole.playapplication.login.service.LoginService
import com.manishpatole.playapplication.network.Networking
import com.manishpatole.playapplication.story.service.StoryService
import com.manishpatole.playapplication.theme.ThemeManager
import com.manishpatole.playapplication.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: PlayApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
        application.getSharedPreferences("play-application-project-prefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideThemeManager(sharedPreferences: SharedPreferences): ThemeManager =
        ThemeManager(sharedPreferences = sharedPreferences)

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)

    @Provides
    @Singleton
    fun provideNetwork(): Retrofit =
        Networking.create(
            BuildConfig.BASE_URL,
            application.cacheDir,
            10 * 1024 * 1024 // 10MB
        )

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Provides
    @Singleton
    fun provideStoryService(retrofit: Retrofit): StoryService =
        retrofit.create(StoryService::class.java)

}