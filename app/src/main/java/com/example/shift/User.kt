package com.example.shift

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class User(
    var id: Long,
    //var icon: String,
    var name: String,
    var surname: String,
    var email: String,
    @SerializedName("phone")
    var phoneNumber: String,
)