package com.hilalkara.cryptotracker.data.dto


import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("en")
    val en: String?,
    @SerializedName("de")
    val de: String?
)