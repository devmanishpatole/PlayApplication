package com.manishpatole.playapplication.story.viewmodel

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.manishpatole.playapplication.CoroutinesTestRule
import com.manishpatole.playapplication.story.model.StoryRequestWrapper
import com.manishpatole.playapplication.story.model.TopStory
import com.manishpatole.playapplication.story.repository.StoryDetailRepository
import com.manishpatole.playapplication.story.service.StoryService
import com.manishpatole.playapplication.utils.MockNetworkHelper
import com.manishpatole.playapplication.utils.NetworkHelper
import com.manishpatole.playapplication.utils.Status
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class StoryDetailViewModelTest {

    private lateinit var viewModel: StoryDetailViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var service: StoryService

    @Mock
    lateinit var context: Context

    private lateinit var repository: StoryDetailRepository
    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        networkHelper = MockNetworkHelper(context)
        repository = StoryDetailRepository(service)
        viewModel = StoryDetailViewModel(networkHelper, repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getStoryDetailsWhenNoInternet() = coroutinesTestRule.testDispatcher.runBlockingTest {
        viewModel.storyRequestWrapperObserver.observeForever {
            assertNotNull(it)
            assertEquals(Status.NO_INTERNET, it.status)
        }
        viewModel.getStoryDetail(StoryRequestWrapper(10, null))
        verify(service, never()).getStoryDetail(any())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getStoryDetails() = coroutinesTestRule.testDispatcher.runBlockingTest {
        (networkHelper as MockNetworkHelper).isConnected = true
        viewModel.getStoryDetail(StoryRequestWrapper(10, null))
        verify(service).getStoryDetail(any())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getStoryDetailsResponse() = coroutinesTestRule.testDispatcher.runBlockingTest {
        (networkHelper as MockNetworkHelper).isConnected = true
        val story = TopStory("Author", 1, 10, ArrayList(), 10, 10, "title", "type", "url")
        val res = Mockito.mock(Response::class.java)
        doReturn(true).`when`(res).isSuccessful
        doReturn(story).`when`(res).body()
        doReturn(res).`when`(service).getStoryDetail(any())

        viewModel.storyRequestWrapperObserver.observeForever {
            assertNotNull(it)
            assertEquals(Status.SUCCESS, it.status)
            assertNotNull(it.data)
        }

        viewModel.getStoryDetail(StoryRequestWrapper(10, null))
        verify(service).getStoryDetail(any())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getStoryDetailsWithErrorResponse() = coroutinesTestRule.testDispatcher.runBlockingTest {
        (networkHelper as MockNetworkHelper).isConnected = true
        val res = Mockito.mock(Response::class.java)
        doReturn(false).`when`(res).isSuccessful
        doReturn(null).`when`(res).body()
        doReturn(res).`when`(service).getStoryDetail(any())

        viewModel.storyRequestWrapperObserver.observeForever {
            assertNotNull(it)
            assertEquals(Status.ERROR, it.status)
            assertNull(it.data)
        }

        viewModel.getStoryDetail(StoryRequestWrapper(10, null))
        verify(service).getStoryDetail(any())
    }

}