package com.manishpatole.playapplication.utils

import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern

fun validateEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

fun validatePassword(password: String): Boolean {
    val pattern: Pattern
    val matcher: Matcher
    val passwordPattern =
        "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,16}$"
    pattern = Pattern.compile(passwordPattern)
    matcher = pattern.matcher(password)

    return matcher.matches()
}