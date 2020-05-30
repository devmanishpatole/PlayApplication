package com.manishpatole.playapplication.story.viewholder

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.manishpatole.playapplication.R
import com.manishpatole.playapplication.base.BaseItemViewHolder
import com.manishpatole.playapplication.di.component.ViewHolderComponent
import com.manishpatole.playapplication.story.model.StoryRequestWrapper
import com.manishpatole.playapplication.story.viewmodel.StoryDetailViewModel
import com.manishpatole.playapplication.utils.Status
import com.manishpatole.playapplication.utils.extension.hide
import com.manishpatole.playapplication.utils.extension.invisible
import com.manishpatole.playapplication.utils.extension.show
import kotlinx.android.synthetic.main.row_layout.view.*

class StoryViewHolder(parent: ViewGroup, private val onItemClick: (Int) -> Unit) :
    BaseItemViewHolder<StoryRequestWrapper, StoryDetailViewModel>(R.layout.row_layout, parent) {

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) =
        viewHolderComponent.inject(this)

    override fun setupView(view: View) {
    }

    override fun bind(data: StoryRequestWrapper) {
        itemView.tag = data.id

        if (null == data.topStory) {
            viewModel.getStoryDetail(data)
        } else {
            displayStoryDetail(data)
        }
    }

    private fun displayStoryDetail(data: StoryRequestWrapper) {
        if (itemView.tag == data.id) {
            itemView.progressBarWrapper.hide()
            itemView.content.show()
            itemView.storyNumber.text = data.number.toString()
            itemView.title.text = data.topStory?.title
            itemView.author.text = data.topStory?.author
        }
    }

    override fun initObserver() {
        viewModel.storyRequestWrapperObserver.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> displayStoryDetail(it.data as StoryRequestWrapper)
                Status.NO_INTERNET -> showError()
                else -> showError(itemView.context.getString(R.string.something_went_wrong))
            }
        })

        itemView.setOnClickListener {
            onItemClick(adapterPosition)
        }
    }

    private fun showError(error: String? = null) {
        itemView.progressBarWrapper.show()
        error?.let {
            itemView.errorMessage.text = it
        }
        itemView.errorMessage.show()
        itemView.progressBar.hide()
        itemView.content.invisible()
    }
}