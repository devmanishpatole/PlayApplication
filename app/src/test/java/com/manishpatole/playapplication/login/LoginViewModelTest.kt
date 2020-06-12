package com.manishpatole.playapplication.login

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.manishpatole.playapplication.CoroutinesTestRule
import com.manishpatole.playapplication.R
import com.manishpatole.playapplication.login.model.LoginRequest
import com.manishpatole.playapplication.login.model.LoginStatus
import com.manishpatole.playapplication.login.model.LoginSuccess
import com.manishpatole.playapplication.login.repository.LoginRepository
import com.manishpatole.playapplication.login.viewmodel.LoginViewModel
import com.manishpatole.playapplication.utils.MockNetworkHelper
import com.manishpatole.playapplication.utils.NetworkHelper
import com.manishpatole.playapplication.utils.Result
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
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.net.ssl.HttpsURLConnection


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var loginRepository: LoginRepository

    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        networkHelper = MockNetworkHelper(context)
        viewModel =
            LoginViewModel(
                networkHelper,
                loginRepository
            )
    }

    @Test
    fun testValidateEmailAndPassword() {
        assertFalse(viewModel.validateEmailAndPassword("", ""))
        assertFalse(viewModel.validateEmailAndPassword("manmanamnam", "123123123123"))
        assertTrue(viewModel.validateEmailAndPassword("man@info.com", "man!Info1"))
    }

    @Test
    fun testPerformLoginWithNoInternet() {
        val request = LoginRequest("man@info.com", "man!Info1")
        viewModel.performLogin(request)
        assertEquals(Result.noInternetError(null), viewModel.loginResponse.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testPerformLogin() = coroutinesTestRule.testDispatcher.runBlockingTest {
        (networkHelper as MockNetworkHelper).isConnected = true
        val request = LoginRequest("man@info.com", "man!Info1")
        viewModel.performLogin(request)
        assertEquals(Result.loading(null), viewModel.loginResponse.value)
        verify(loginRepository).login(any())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testPerformLoginCompleteWithSuccess() = coroutinesTestRule.testDispatcher.runBlockingTest {
        (networkHelper as MockNetworkHelper).isConnected = true
        val request = LoginRequest("man@info.com", "man!Info1")

        val loginStatus = LoginStatus.Success(LoginSuccess("XYZ"))
        doReturn(loginStatus).`when`(loginRepository).login(request)

        viewModel.loginResponse.observeForever { result ->
            when (result.status) {
                Status.LOADING -> assertNull(result.data)
                Status.SUCCESS -> assertEquals("XYZ", result.data)
                else -> fail()
            }
        }

        viewModel.performLogin(request)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testPerformLoginWithBadRequestError() = coroutinesTestRule.testDispatcher.runBlockingTest {
        (networkHelper as MockNetworkHelper).isConnected = true
        val request = LoginRequest("man@info.com", "man!Info1")

        val loginStatus = LoginStatus.Failure(400)
        doReturn(loginStatus).`when`(loginRepository).login(request)

        viewModel.loginResponse.observeForever { result ->
            when (result.status) {
                Status.LOADING -> assertNull(result.data)
                Status.ERROR -> {
                    assertEquals(
                        HttpsURLConnection.HTTP_BAD_REQUEST,
                        (result.data as LoginStatus.Failure).error
                    )
                    assertEquals(
                        R.string.bad_request,
                        (result.data as LoginStatus.Failure).errorMessageId
                    )
                }
                else -> fail()
            }
        }

        viewModel.performLogin(request)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testPerformLoginWithUnauthorisedError() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            (networkHelper as MockNetworkHelper).isConnected = true
            val request = LoginRequest("man@info.com", "man!Info1")

            val loginStatus = LoginStatus.Failure(401)
            doReturn(loginStatus).`when`(loginRepository).login(request)

            viewModel.loginResponse.observeForever { result ->
                when (result.status) {
                    Status.LOADING -> assertNull(result.data)
                    Status.ERROR -> {
                        assertEquals(
                            HttpsURLConnection.HTTP_UNAUTHORIZED,
                            (result.data as LoginStatus.Failure).error
                        )
                        assertEquals(
                            R.string.unauthorised,
                            (result.data as LoginStatus.Failure).errorMessageId
                        )
                    }
                    else -> fail()
                }
            }

            viewModel.performLogin(request)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun testPerformLoginWithUnexpectedError() = coroutinesTestRule.testDispatcher.runBlockingTest {
        (networkHelper as MockNetworkHelper).isConnected = true
        val request = LoginRequest("man@info.com", "man!Info1")

        val loginStatus = LoginStatus.Failure(500)
        doReturn(loginStatus).`when`(loginRepository).login(request)

        viewModel.loginResponse.observeForever { result ->
            when (result.status) {
                Status.LOADING -> assertNull(result.data)
                Status.ERROR -> {
                    assertEquals(
                        HttpsURLConnection.HTTP_SERVER_ERROR,
                        (result.data as LoginStatus.Failure).error
                    )
                    assertEquals(
                        R.string.something_went_wrong,
                        (result.data as LoginStatus.Failure).errorMessageId
                    )
                }
                else -> fail()
            }
        }

        viewModel.performLogin(request)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testPerformLoginWithoutInternetWithObserver() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val request = LoginRequest("man@info.com", "man!Info1")
            viewModel.loginResponse.observeForever {
                assertNotNull(it)
                assertEquals(Status.NO_INTERNET, it.status)
            }

            viewModel.performLogin(request)
            verify(loginRepository, never()).login(any())
        }

}
