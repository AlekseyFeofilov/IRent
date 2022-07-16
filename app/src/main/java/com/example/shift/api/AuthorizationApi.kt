package com.example.shift.api

import com.example.shift.User
import com.example.shift.authorization.data.ConfirmData
import com.example.shift.authorization.data.SecurityData
import com.example.shift.authorization.data.SecurityExtendData
import com.example.shift.authorization.data.SignUpData
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.security.Security

interface AuthorizationApi {
    @POST("/login")
    fun logIn(@Body security: SecurityData): Call<User>

    @PUT("/login")
    fun signUp(@Body signUpData: SignUpData): Call<String>

    @PATCH("/login")
    fun confirmUser(@Body confirmData: ConfirmData): Call<User>
}