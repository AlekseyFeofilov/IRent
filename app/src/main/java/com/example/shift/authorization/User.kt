package com.example.shift.authorization

data class User(
    var id: Long,
    var name: String,
    var surname: String,
    var email: String,
    var phoneNumber: String,
)