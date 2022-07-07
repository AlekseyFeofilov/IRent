package com.example.shift

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SampleApp : Application() {

    companion object{
        val retrofit = Retrofit.Builder()
            .baseUrl("") //todo: set URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}