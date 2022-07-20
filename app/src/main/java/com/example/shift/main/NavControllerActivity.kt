package com.example.shift.main

interface NavControllerActivity {
    fun showMessage(message: String)

    fun goTo(fragmentId: Int)
}