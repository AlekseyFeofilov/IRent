package com.example.shift.authorization

import com.example.shift.authorization.data.Security
import com.example.shift.authorization.data.User
import retrofit2.Call
import retrofit2.http.*

interface AuthorizationApi {
    @GET("get/users/{login}{password}")
    fun getUser(@Path("login") login: String, @Path("password") password: String): Call<User>

    @GET("get/security/{email}")
    fun contains(@Path("email") email: String): Call<Boolean>

    @PUT("post/user/new")
    fun confirmUser(@Body id: Long?, @Body code: String): Call<User>

    @POST("post/user/new")
    fun createUser(@Body security: Security, @Body user: User): Call<Long>
}