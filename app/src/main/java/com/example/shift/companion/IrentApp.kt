package com.example.shift.companion

import android.app.Application
import com.example.shift.api.AuthorizationApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class IrentApp : Application() {

    companion object {
        const val backendToggle = false
        const val confirmEmail = true
        const val signUp = true
        const val logIn = true
        const val captchaToggle = true
        const val validationToggle = true

        private val interceptor: HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.MINUTES)
            .writeTimeout(60, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.MINUTES).addInterceptor(interceptor).build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://irental.ddns.net/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build()
    }
}