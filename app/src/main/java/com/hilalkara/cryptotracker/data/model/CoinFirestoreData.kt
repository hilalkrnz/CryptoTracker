package com.hilalkara.cryptotracker.data.model

import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName

@Keep
data class CoinFirestoreData @JvmOverloads constructor(
    @get:PropertyName("id") @set:PropertyName("id") var id: String = "",
    @get:PropertyName("name") @set:PropertyName("name") var name: String = "",
    @get:PropertyName("symbol") @set:PropertyName("symbol") var symbol: String = "",
    @get:PropertyName("image") @set:PropertyName("image") var image: String = "",
    @get:PropertyName("currentPrice") @set:PropertyName("currentPrice") var currentPrice: Double = 0.0,
    @get:PropertyName("high24h") @set:PropertyName("high24h") var high24h: Double = 0.0,
    @get:PropertyName("low24h") @set:PropertyName("low24h") var low24h: Double = 0.0,
    @get:PropertyName("priceChangePercentage24h") @set:PropertyName("priceChangePercentage24h") var priceChangePercentage24h: Double = 0.0,
)
