package com.example.shift.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedItem(
    val id: Long,
    val imagesURL: List<String>,
    @Json(name = "title") val cardName: String?,
    val description: String?,
    @Json(name = "price") val cardPrice: Long?,
    val ownerName: String?,
    val ownerSurname: String?,
    val ownerPhone: String?,
    val rent: Boolean?,
    val customerName: String?,
    val customerSurname: String?,
    val customerPhone: String?,
    val term: String?,
    val startDate: String?,
    val finishDate: String?,
    val category: String?
)
