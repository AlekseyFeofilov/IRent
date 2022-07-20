package com.example.shift.companion

import android.content.SharedPreferences
import com.example.shift.User
import com.google.gson.Gson

class SharedPreferencesObject {
    companion object {
        var user: User? = null

        const val APP_PREFERENCES_USER = "User"
        const val APP_PREFERENCES_ID = "ID"
        const val APP_PREFERENCES = "Settings"

        fun saveUser(sharedPreferences: SharedPreferences){
            sharedPreferences
                .edit()
                .putString(APP_PREFERENCES_USER, Gson().toJson(user))
                .apply()
        }

        fun deleteUser(sharedPreferences: SharedPreferences){
            sharedPreferences
                .edit()
                .remove(APP_PREFERENCES_USER)
                .apply()
        }
    }

}