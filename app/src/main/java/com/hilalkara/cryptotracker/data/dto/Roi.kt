package com.hilalkara.cryptotracker.data.dto


import com.google.gson.annotations.SerializedName

data class Roi(
    @SerializedName("times")
    val times: Double?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("percentage")
    val percentage: Double?
)