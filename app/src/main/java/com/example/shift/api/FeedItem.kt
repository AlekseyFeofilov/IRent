package com.example.shift.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedItem(
    @Json(name = "title") val cardName: String,
    @Json(name = "id") val cardPrice: String,
    //val title: String,
    //val id: String,
    @Json(name = "url_s") val cardPhotoUrl: String,
)
