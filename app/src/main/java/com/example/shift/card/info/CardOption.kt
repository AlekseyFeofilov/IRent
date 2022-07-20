package com.example.shift.card.info

interface CardOption {
    fun editCard(cardInfo: CardInfo)
    fun deleteCard(cardInfo: CardInfo)
    fun endCardRenting(cardInfo: CardInfo)
}