package com.manishpatole.playapplication.story.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.manishpatole.playapplication.R
import com.manishpatole.playapplication.base.BaseActivity
import com.manishpatole.playapplication.di.component.ActivityComponent
import com.manishpatole.playapplication.login.ui.LoginActivity
import com.manishpatole.playapplication.splash.LoginManagerViewModel
import com.manishpatole.playapplication.story.model.TopStory
import com.manishpatole.playapplication.story.viewmodel.SharedViewModel
import com.manishpatole.playapplication.utils.extension.replaceFragment
import com.manishpatole.playapplication.utils.extension.replaceFragmentWithBackStack

class StoryActivity : BaseActivity<LoginManagerViewModel>() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun getLayoutId() = R.layout.activity_story

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        if (null == savedInstanceState) {
            launchListFragment()
        }
        initObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                viewModel.logoutUser()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initObserver() {
        sharedViewModel.storyDetailLauncher.observe(this, Observer { topStory ->
            topStory?.let {
                launchDetailFragment(it)
            }
        })
    }

    private fun launchDetailFragment(topStory: TopStory) =
        replaceFragmentWithBackStack(StoryDetailFragment.newInstance(topStory), R.id.viewContainer)

    private fun launchListFragment() =
        replaceFragment(StoryListFragment.newInstance(), R.id.viewContainer)

}
