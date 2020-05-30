package com.manishpatole.playapplication.story.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.manishpatole.playapplication.R
import com.manishpatole.playapplication.base.BaseFragment
import com.manishpatole.playapplication.di.component.FragmentComponent
import com.manishpatole.playapplication.story.model.TopStory
import com.manishpatole.playapplication.story.viewmodel.DisplayStoryDetailViewModel
import kotlinx.android.synthetic.main.fragment_story_detail.*

class StoryDetailFragment : BaseFragment<DisplayStoryDetailViewModel>() {

    companion object {
        private const val TOP_STORY = "TOP_STORY"
        fun newInstance(topStory: TopStory): StoryDetailFragment {
            val fragment = StoryDetailFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(TOP_STORY, topStory)
            }
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_story_detail

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

    override fun setupView(view: View) {
        val topStory = arguments?.getParcelable<TopStory>(TOP_STORY)
        title.text = topStory?.title
        webView.loadUrl(topStory?.url)
    }

}