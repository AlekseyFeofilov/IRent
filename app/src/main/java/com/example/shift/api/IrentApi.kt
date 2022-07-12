package com.example.shift.api

import retrofit2.http.GET

private const val API_KEY = "c7e223c12c28374c6b1dd3eb02e3a58a"

interface IrentApi {
    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=$API_KEY" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s"
    )
    suspend fun fetchFeeds(): IrentResponse
}