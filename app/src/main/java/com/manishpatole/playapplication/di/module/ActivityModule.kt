package com.manishpatole.playapplication.di.module

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import com.manishpatole.playapplication.base.BaseActivity
import com.manishpatole.playapplication.base.ViewModelProviderFactory
import com.manishpatole.playapplication.login.repository.LoginRepository
import com.manishpatole.playapplication.login.viewmodel.LoginViewModel
import com.manishpatole.playapplication.splash.LoginManagerViewModel
import com.manishpatole.playapplication.utils.NetworkHelper
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideLoginViewModel(
        networkHelper: NetworkHelper,
        loginRepository: LoginRepository
    ): LoginViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(LoginViewModel::class) {
            LoginViewModel(
                networkHelper,
                loginRepository
            )
        }).get(LoginViewModel::class.java)

    @Provides
    fun provideLoginManagerViewModel(
        networkHelper: NetworkHelper,
        sharedPreferences: SharedPreferences
    ): LoginManagerViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(LoginManagerViewModel::class) {
            LoginManagerViewModel(
                networkHelper,
                sharedPreferences
            )
        }).get(LoginManagerViewModel::class.java)
}