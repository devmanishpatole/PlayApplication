package com.manishpatole.playapplication.login.ui

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.manishpatole.playapplication.CoroutinesTestRule
import com.manishpatole.playapplication.login.repository.LoginRepository
import com.manishpatole.playapplication.login.viewmodel.LoginViewModel
import com.manishpatole.playapplication.utils.MockNetworkHelper
import com.manishpatole.playapplication.utils.NetworkHelper
import com.manishpatole.playapplication.utils.Status
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class LoginActivityTest {

    private lateinit var activity: LoginActivity

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var loginViewModel: LoginViewModel

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var loginRepository: LoginRepository

    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        networkHelper = MockNetworkHelper(context)
        loginViewModel =
            LoginViewModel(
                networkHelper,
                loginRepository
            )
        activity = Robolectric.buildActivity(LoginActivity::class.java).create().resume().get()
        activity.viewModel = loginViewModel
    }

    @ExperimentalCoroutinesApi
    @Test
    fun performLoginClickWithValidDetails() = coroutinesTestRule.testDispatcher.runBlockingTest {
        activity.editUsername.setText("man@man.com")
        activity.editPassword.setText("Play@123")
        enableInternet()

        activity.viewModel.loginResponse.observeForever { loading ->
            assertNotNull(loading)
            assertEquals(Status.LOADING, loading.status)
        }
        activity.runOnUiThread {
            activity.loginButton.performClick()
        }
        verify(loginRepository).login(any())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun performLoginClickLoginNeverHappens() = coroutinesTestRule.testDispatcher.runBlockingTest {
        activity.editUsername.setText("")
        activity.editPassword.setText("")
        enableInternet()

        activity.runOnUiThread {
            activity.loginButton.performClick()
        }
        verify(loginRepository, never()).login(any())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun performLoginClickShowsEmailError() = coroutinesTestRule.testDispatcher.runBlockingTest {
        activity.editUsername.setText("")
        activity.editPassword.setText("Play@123")
        enableInternet()

        activity.viewModel.emailValidation.observeForever { validEmail ->
            assertFalse(validEmail)
        }
        activity.runOnUiThread {
            activity.loginButton.performClick()
        }
        verify(loginRepository, never()).login(any())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun performLoginClickShowsPasswordError() = coroutinesTestRule.testDispatcher.runBlockingTest {
        activity.editUsername.setText("")
        activity.editPassword.setText("Play@123")
        enableInternet()

        activity.viewModel.passwordValidation.observeForever { validPassword ->
            assertFalse(validPassword)
        }
        activity.runOnUiThread {
            activity.loginButton.performClick()
        }
        verify(loginRepository, never()).login(any())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun performLoginClickWithNoInternet() = coroutinesTestRule.testDispatcher.runBlockingTest {
        activity.editUsername.setText("man@man.com")
        activity.editPassword.setText("Play@123")

        activity.viewModel.loginResponse.observeForever { noInternet ->
            assertNotNull(noInternet)
            assertEquals(Status.NO_INTERNET, noInternet.status)
        }
        activity.runOnUiThread {
            activity.loginButton.performClick()
        }
        verify(loginRepository, never()).login(any())
    }

    private fun enableInternet() {
        (networkHelper as MockNetworkHelper).isConnected = true
    }

}