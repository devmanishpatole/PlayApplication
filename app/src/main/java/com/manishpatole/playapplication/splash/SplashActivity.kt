package com.manishpatole.playapplication.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.manishpatole.playapplication.R
import com.manishpatole.playapplication.base.BaseActivity
import com.manishpatole.playapplication.di.component.ActivityComponent
import com.manishpatole.playapplication.login.ui.LoginActivity
import com.manishpatole.playapplication.story.ui.StoryActivity


class SplashActivity : BaseActivity<LoginManagerViewModel>() {

    override fun getLayoutId() = R.layout.activity_splash

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    companion object {
        private const val SPLASH_TIME_OUT = 3000L
    }

    override fun beforeOnCreate() {
        //Making activity full screen
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        Handler().postDelayed({
            if (viewModel.isUserLoggedIn()) {
                startActivity(Intent(this@SplashActivity, StoryActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
            finish()
        }, SPLASH_TIME_OUT)
    }
}
