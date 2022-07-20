package com.example.shift.card.info

import com.google.gson.annotations.SerializedName

data class CardInfo(
    val id: Long,
    var title: String,
    val ownerName: String,
    val ownerSurname: String,
    var price: Long,
    @SerializedName("rent")
    var status: Boolean?,
    var term: String
)
