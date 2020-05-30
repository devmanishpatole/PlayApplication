package com.manishpatole.playapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.manishpatole.playapplication.R
import com.manishpatole.playapplication.application.PlayApplication
import com.manishpatole.playapplication.di.component.DaggerFragmentComponent
import com.manishpatole.playapplication.di.component.FragmentComponent
import com.manishpatole.playapplication.di.module.FragmentModule
import com.manishpatole.playapplication.utils.extension.hide
import com.manishpatole.playapplication.utils.extension.show
import kotlinx.android.synthetic.main.base_layout.*
import kotlinx.android.synthetic.main.base_layout.view.*
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildFragmentComponent())
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    private fun buildFragmentComponent() =
        DaggerFragmentComponent
            .builder()
            .applicationComponent((context!!.applicationContext as PlayApplication).applicationComponent)
            .fragmentModule(FragmentModule(this))
            .build()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.base_layout, container, false)
        val content = inflater.inflate(getLayoutId(), container, false)
        rootView.mainViewContainer.addView(content)
        return rootView
    }

    protected fun showProgressbar() {
        mainViewContainer.hide()
        progressText.hide()
        errorText.hide()
        progressbar.show()
    }

    protected fun hideProgressbarOrError() {
        mainViewContainer.show()
        progressText.hide()
        errorText.hide()
        progressbar.hide()
    }

    protected fun showProgressbarWithText(text: String) {
        progressText.text = text
        mainViewContainer.hide()
        progressText.show()
        progressbar.show()
    }

    protected fun showErrorText(text: String) {
        errorText.text = text
        errorText.show()
        mainViewContainer.hide()
        progressText.hide()
        progressbar.hide()
    }

    protected open fun setupObservers() {
        viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
    }

    fun showMessage(message: String) =
        context?.let { Toast.makeText(it, message, Toast.LENGTH_SHORT).show() }

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun injectDependencies(fragmentComponent: FragmentComponent)

    protected abstract fun setupView(view: View)
}