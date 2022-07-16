package com.example.shift.api

import com.example.shift.authorization.data.SecurityExtendData
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface SettingsApi {
    @DELETE("/me/settings")
    fun deleteUser(@Body securityExtendData: SecurityExtendData): Call<Boolean>

    @PUT("/login/icon")
    @Multipart
    fun addIcon(@Part file: MultipartBody.Part, @Part id: MultipartBody.Part)

    @GET("/login")
    fun checkBackend(): Call<ResponseBody>
}