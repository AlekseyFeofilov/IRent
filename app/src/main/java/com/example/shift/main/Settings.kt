package com.example.shift.main

import android.content.SharedPreferences
import com.example.shift.User

interface Settings {
    fun getSharedPreferences(): SharedPreferences
    fun getID(): Long
    fun getUser(): User?
}