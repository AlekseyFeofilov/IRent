package com.example.shift.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IrentResponse(
    val photos: PhotoResponse
)
