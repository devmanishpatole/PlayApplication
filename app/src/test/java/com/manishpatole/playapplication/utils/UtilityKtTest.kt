package com.manishpatole.playapplication.utils

import android.os.Build
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class UtilityKtTest {

    @Test
    fun testValidatePassword() {
        assertFalse(validatePassword(""))
        assertFalse(validatePassword("abc"))
        assertFalse(validatePassword("12345678"))
        assertFalse(validatePassword("123Abc#"))
        assertFalse(validatePassword("123Abc#akdkdsakdkagsdkbaskjdb"))

        assertTrue(validatePassword("123Abc#$"))
        assertTrue(validatePassword("123Abc#$123"))
    }

    @Test
    fun testValidateEmail() {
        assertFalse(validateEmail(""))
        assertFalse(validateEmail("abcadess"))
        assertFalse(validateEmail("man@man.com,com"))

        assertTrue(validateEmail("man@man.com"))
        assertTrue(validateEmail("info@play.com"))
    }
}