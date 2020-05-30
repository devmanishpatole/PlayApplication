package com.manishpatole.playapplication.utils.extension

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun FragmentActivity.replaceFragment(fragment: Fragment, @IdRes holder: Int) {
    supportFragmentManager.beginTransaction()
        .replace(holder, fragment).commit()
}

fun FragmentActivity.replaceFragmentWithBackStack(
    fragment: Fragment,
    @IdRes holder: Int,
    backStack: String? = null
) {
    supportFragmentManager.beginTransaction().addToBackStack(backStack)
        .replace(holder, fragment).commit()
}


