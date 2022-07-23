package com.example.shift.card.info

import com.google.gson.annotations.SerializedName

data class CardAndUserId(
    @SerializedName("cardID")
    val cardId: Long,
    @SerializedName("userID")
    val userId: Long,
)
