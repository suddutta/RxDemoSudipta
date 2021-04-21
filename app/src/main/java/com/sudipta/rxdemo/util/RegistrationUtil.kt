package com.sudipta.rxdemo.util

object RegistrationUtil {

    private val existingUsers = listOf("Sudipta", "Arsh", "Geet")

    /**
     * the input is not valid if...
     * ...the username/password is empty
     * ...the username is already taken
     */
    fun validateRegistrationInput(
        username: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        if (username.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty()) {
            return false
        }
        if (username in existingUsers) {
            return false
        }

        return true
    }

    /**
     * * ...the confirmed password is not the same as the real password
     * ...the password contains less than 2 digits
     */


    fun validateRegistrationInputPassword(
        username: String,
        password: String,
        confirmedPassword: String
    ): Boolean {

        if (username.isEmpty()) {
            return false
        }

        if (password != confirmedPassword) {
            return false
        }

        if (password.count { it.isDigit() } < 2) {
            return false
        }

        return true
    }
}




