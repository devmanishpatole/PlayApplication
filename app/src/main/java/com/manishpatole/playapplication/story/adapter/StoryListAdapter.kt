package com.manishpatole.playapplication.story.adapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.manishpatole.playapplication.base.BaseAdapter
import com.manishpatole.playapplication.story.model.StoryRequestWrapper
import com.manishpatole.playapplication.story.model.TopStory
import com.manishpatole.playapplication.story.viewholder.StoryViewHolder

class StoryListAdapter(parentLifecycle: Lifecycle,dataList: ArrayList<StoryRequestWrapper>) :
    BaseAdapter<StoryRequestWrapper, StoryViewHolder>(parentLifecycle, dataList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StoryViewHolder(parent, ::onClick)

    lateinit var onItemClick: (TopStory) -> Unit

    private fun onClick(position : Int) {
        if (::onItemClick.isInitialized){
            dataList[position].topStory?.let { onItemClick(it) }
        }
    }

}