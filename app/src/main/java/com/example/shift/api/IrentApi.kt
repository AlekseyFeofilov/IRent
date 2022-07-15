package com.example.shift.api

import retrofit2.http.GET
import retrofit2.http.Query

interface IrentApi {
    @GET(
        "feed?from=0&to=114"
    )
    suspend fun fetchFeeds(): List<FeedItem>

    @GET(
        "cards?"
    )
    suspend fun fetchCard(@Query("id") id: Long): FeedItem
}