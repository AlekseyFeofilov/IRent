package com.example.shift.api

import com.example.shift.card.data.NewCard
import com.example.shift.card.info.CardAndUserId
import com.example.shift.card.info.CardInfo
import retrofit2.Call
import retrofit2.http.*

interface CardApi {
    @POST("/me/own")
    fun getOwnCards(@Body id: Long): Call<List<Long>>

    @POST("/me/rent")
    fun getRentedCards(@Body id: Long): Call<List<Long>>

    @GET("/cards")
    fun getCard(id: Long): Call<CardInfo>

    @PUT("/me/own")
    fun createCard(newCard: NewCard): Call<Boolean>

    @DELETE("/me/own")
    fun deleteCard(cardAndUserId: CardAndUserId): Call<Boolean>
}