package com.hilalkara.cryptotracker.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinUiData(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    val currentPrice: String,
    val high24h: Double,
    val low24h: Double,
    val priceChangePercentage24h: Double
) : Parcelable