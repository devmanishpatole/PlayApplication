package com.manishpatole.playapplication.application

import android.app.Application
import com.manishpatole.playapplication.di.component.ApplicationComponent
import com.manishpatole.playapplication.di.component.DaggerApplicationComponent
import com.manishpatole.playapplication.di.module.ApplicationModule

class PlayApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder().applicationModule(
            ApplicationModule(this)
        ).build()
        applicationComponent.inject(this)
    }

}