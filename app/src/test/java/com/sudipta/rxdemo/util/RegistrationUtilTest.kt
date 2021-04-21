package com.sudipta.rxdemo.util


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest  {

    @Test
    fun emptyUsernameTest() {
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun bothPasswordSameTest() {
        val result = RegistrationUtil.validateRegistrationInputPassword(
            "Sudipta",
            "123",
            "123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun existUsernameTest() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Arsh",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun passwordMatchTest() {
        val result = RegistrationUtil.validateRegistrationInputPassword(
            "Geet",
            "123456",
            "abcdefg"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun emptyPasswordTest() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Sudipta",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun twoDigitPasswordTest() {
        val result = RegistrationUtil.validateRegistrationInputPassword(
            "Sudipta",
            "abcdefg5",
            "abcdefg5"
        )
        assertThat(result).isFalse()
    }

}