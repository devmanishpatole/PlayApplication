package com.manishpatole.playapplication.theme

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Singleton

@Singleton
class ThemeManager(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val DARK_THEME = "DARK_THEME"
    }

    fun isDarkTheme() = sharedPreferences.getBoolean(DARK_THEME, false)

    fun changeTheme(isDarkTheme: Boolean, themeChanged: () -> Unit) {
        sharedPreferences.edit { putBoolean(DARK_THEME, isDarkTheme) }
        themeChanged()
    }


}