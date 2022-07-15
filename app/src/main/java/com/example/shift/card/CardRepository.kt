package com.example.shift.card

import com.example.shift.api.FeedItem
import com.example.shift.api.IrentApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class CardRepository {
    private val irentApi: IrentApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://irental.ddns.net/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        irentApi = retrofit.create()
    }

    suspend fun fetchCard(id: Long): FeedItem = irentApi.fetchCard(id)
}