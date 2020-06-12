package com.manishpatole.playapplication.story.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.manishpatole.playapplication.R
import com.manishpatole.playapplication.base.BaseFragment
import com.manishpatole.playapplication.di.component.FragmentComponent
import com.manishpatole.playapplication.story.adapter.StoryListAdapter
import com.manishpatole.playapplication.story.model.StoryRequestWrapper
import com.manishpatole.playapplication.story.viewmodel.SharedViewModel
import com.manishpatole.playapplication.story.viewmodel.StoryListViewModel
import com.manishpatole.playapplication.utils.Status
import com.manishpatole.playapplication.utils.extension.hide
import com.manishpatole.playapplication.utils.extension.show
import kotlinx.android.synthetic.main.fragment_story_list.*
import javax.inject.Inject

class StoryListFragment : BaseFragment<StoryListViewModel>() {

    private lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var listAdapter: StoryListAdapter

    companion object {
        fun newInstance() = StoryListFragment()
    }

    override fun getLayoutId() = R.layout.fragment_story_list

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun setupView(view: View) {
        initObservers()
        initSwipeRefresh()
        loadData()
    }

    private fun initSwipeRefresh() {
        refresh.setOnClickListener {
            loadData()
        }

        swipeRefresh.setOnRefreshListener {
            loadData()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun initObservers() {
        viewModel.stories.observe(viewLifecycleOwner, Observer { resource ->

            resource?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        hideRefresh()
                        swipeRefresh.isRefreshing = false
                        hideProgressbarOrError()
                        loadDetails(it.data as List<Int>)
                    }
                    Status.LOADING -> {
                        showProgressbarWithText(getString(R.string.loading))
                    }
                    Status.NO_INTERNET -> {
                        swipeRefresh.isRefreshing = false
                        hideProgressbarOrError()
                        showRefresh()
                        showMessage(R.string.no_internet)
                    }
                    else -> {
                        swipeRefresh.isRefreshing = false
                        hideProgressbarOrError()
                        showRefresh()
                        showMessage(getString(R.string.something_went_wrong))
                    }
                }
            }
        })
    }

    private fun loadData() {
        viewModel.loadStories()
    }

    private fun showRefresh() {
        list.hide()
        refresh.show()
        refreshText.show()
    }

    private fun hideRefresh() {
        list.show()
        refresh.hide()
        refreshText.hide()
    }

    private fun loadDetails(data: List<Int>) {
        viewModel.numberList?.takeIf { it.isNotEmpty() }?.let { list ->
            listAdapter.appendData(list)
        } ?: run {
            val numberList = ArrayList<StoryRequestWrapper>()
            for (i in data) {
                val response = StoryRequestWrapper(i, null)
                numberList.add(response)
            }
            viewModel.numberList = numberList
            listAdapter.appendData(numberList)
        }

        listAdapter.onItemClick = {
            sharedViewModel.launchDetail(it)
        }

        list.run {
            adapter = listAdapter
        }
    }

    override fun onDestroyView() {
        list.adapter = null
        super.onDestroyView()
    }

}