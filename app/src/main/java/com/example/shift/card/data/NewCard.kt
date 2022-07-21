package com.example.shift.card.data

data class NewCard(
    val title: String,
    val category: String,
    val price: Long,
    val description: String,
    val term: String,
    val userID: Long
)