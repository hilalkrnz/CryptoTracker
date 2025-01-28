package com.hilalkara.cryptotracker.data.dto


import com.google.gson.annotations.SerializedName

data class CoinDetailResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("block_time_in_minutes")
    val blockTimeInMinutes: Int?,
    @SerializedName("hashing_algorithm")
    val hashingAlgorithm: String?,
    @SerializedName("categories")
    val categories: List<String>?,
    @SerializedName("description")
    val description: Description?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("genesis_date")
    val genesisDate: String?,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int?,
    @SerializedName("market_data")
    val marketData: MarketData?,
    @SerializedName("last_updated")
    val lastUpdated: String?
)