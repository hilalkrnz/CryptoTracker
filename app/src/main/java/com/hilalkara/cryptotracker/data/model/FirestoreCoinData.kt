package com.hilalkara.cryptotracker.data.model

data class FirestoreCoinData(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val high24h: Double,
    val low24h: Double,
    val priceChangePercentage24h: Double,
    val isFavorite: Boolean
)
