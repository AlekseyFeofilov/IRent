package com.example.shift.api

import com.example.shift.card.own.UserId
import com.example.shift.card.data.NewCard
import com.example.shift.card.info.CardAndUserId
import com.example.shift.card.info.CardInfo
import retrofit2.Call
import retrofit2.http.*

interface CardApi {
    @POST("/me/own")
    fun getOwnCards(@Body userId: UserId): Call<List<Long>>

    @POST("/me/rent")
    fun getRentedCards(@Body id: Long): Call<List<Long>>

    @GET("/cards")
    fun getCard(@Query("id") id: Long): Call<CardInfo>

    @PUT("/me/own")
    fun createCard(@Body newCard: NewCard): Call<Long>

    @HTTP(method = "DELETE", path = "/me/own", hasBody = true)
    fun deleteCard(@Body cardAndUserId: CardAndUserId): Call<Boolean>
}