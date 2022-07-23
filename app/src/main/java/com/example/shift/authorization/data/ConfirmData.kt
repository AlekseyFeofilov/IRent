package com.example.shift.authorization.data

import com.google.gson.annotations.SerializedName

data class ConfirmData(
    @SerializedName("tempID")
    val id: Long,
    @SerializedName("emailCode")
    val code: String
)
