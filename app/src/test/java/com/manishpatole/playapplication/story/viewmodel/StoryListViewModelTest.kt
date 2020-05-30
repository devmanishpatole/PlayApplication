package com.manishpatole.playapplication.story.viewmodel

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.manishpatole.playapplication.CoroutinesTestRule
import com.manishpatole.playapplication.story.repository.StoryListRepository
import com.manishpatole.playapplication.story.service.StoryService
import com.manishpatole.playapplication.utils.MockNetworkHelper
import com.manishpatole.playapplication.utils.NetworkHelper
import com.manishpatole.playapplication.utils.Status
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class StoryListViewModelTest {

    lateinit var viewModel: StoryListViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var service: StoryService

    @Mock
    lateinit var context: Context

    private lateinit var repository: StoryListRepository
    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        networkHelper = MockNetworkHelper(context)
        repository = StoryListRepository(service)
        viewModel = StoryListViewModel(networkHelper, repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getStoryListWhenNoInternet() = coroutinesTestRule.testDispatcher.runBlockingTest {
        viewModel.stories.observeForever {
            assertNotNull(it)
            assertEquals(Status.NO_INTERNET, it.status)
        }
        viewModel.loadStories()
        verify(service, never()).getStoryList()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun getStoryList() = coroutinesTestRule.testDispatcher.runBlockingTest {
        (networkHelper as MockNetworkHelper).isConnected = true
        viewModel.loadStories()
        verify(service).getStoryList()
    }


}