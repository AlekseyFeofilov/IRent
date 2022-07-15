package com.example.shift.api

import retrofit2.http.GET

interface IrentApi {
    @GET(
        "feed?from=0&to=100"
    )
    suspend fun fetchFeeds(): List<FeedItem>

    @GET(
        "cards?id=31"
    )
    suspend fun fetchCard(): FeedItem
}