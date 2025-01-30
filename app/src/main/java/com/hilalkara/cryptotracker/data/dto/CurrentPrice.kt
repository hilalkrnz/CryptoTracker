package com.hilalkara.cryptotracker.data.dto


import com.google.gson.annotations.SerializedName

data class CurrentPrice(
    @SerializedName("usd")
    val usd: Double?
)