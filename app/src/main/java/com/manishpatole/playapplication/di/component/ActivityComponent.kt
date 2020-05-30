package com.manishpatole.playapplication.di.component

import com.manishpatole.playapplication.login.ui.LoginActivity
import com.manishpatole.playapplication.di.ActivityScope
import com.manishpatole.playapplication.di.module.ActivityModule
import com.manishpatole.playapplication.splash.SplashActivity
import com.manishpatole.playapplication.story.ui.StoryActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {
    fun inject(activity: LoginActivity)

    fun inject(activity: StoryActivity)

    fun inject(activity: SplashActivity)

}