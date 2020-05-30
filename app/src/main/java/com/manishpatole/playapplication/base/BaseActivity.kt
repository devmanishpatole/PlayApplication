package com.manishpatole.playapplication.base

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.manishpatole.playapplication.R
import com.manishpatole.playapplication.application.PlayApplication
import com.manishpatole.playapplication.di.component.ActivityComponent
import com.manishpatole.playapplication.di.component.DaggerActivityComponent
import com.manishpatole.playapplication.di.module.ActivityModule
import com.manishpatole.playapplication.theme.ThemeManager
import com.manishpatole.playapplication.utils.extension.hide
import com.manishpatole.playapplication.utils.extension.show
import kotlinx.android.synthetic.main.base_layout.*
import javax.inject.Inject


abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    @Inject
    lateinit var viewModel: T

    @Inject
    protected lateinit var themeManager: ThemeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        setTheme()
        super.onCreate(savedInstanceState)
        beforeOnCreate()
        setContentView(R.layout.base_layout)
        val content = layoutInflater.inflate(getLayoutId(), null)
        mainViewContainer.addView(content)
        initObserver()
        setupView(savedInstanceState)
    }

    private fun setTheme() {
        if (themeManager.isDarkTheme()) {
            setTheme(R.style.AppTheme_Dark)
        } else {
            setTheme(R.style.AppTheme)
        }
    }

    private fun buildActivityComponent() =
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as PlayApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()

    private fun initObserver() {
        viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })
    }

    protected fun showProgressbar() {
        mainViewContainer.hide()
        progressText.hide()
        progressbar.show()
    }

    protected fun hideProgressbar() {
        mainViewContainer.show()
        progressText.hide()
        progressbar.hide()
    }

    protected fun showProgressbarWithText(text: String) {
        progressText.text = text
        mainViewContainer.hide()
        progressText.show()
        progressbar.show()
    }

    fun hideKeyboard(){
        val inputMethodManager: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    fun showMessage(message: String) =
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    protected open fun beforeOnCreate() {
        // To implement in child activity if required.
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    protected abstract fun setupView(savedInstanceState: Bundle?)

}