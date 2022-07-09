package com.example.shift

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SampleApp : Application() {

    companion object{
        const val backendToggle = false

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("") //todo: set URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}