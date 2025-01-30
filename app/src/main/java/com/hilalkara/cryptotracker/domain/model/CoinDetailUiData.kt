package com.hilalkara.cryptotracker.domain.model

data class CoinDetailUiData(
    val id: String,
    val symbol: String,
    val name: String,
    val hashingAlgorithm: String,
    val description: String,
    val image: String,
    val currentPrice: String,
    val priceChangePercentage24h: Double,
    val lastUpdated: String
)
