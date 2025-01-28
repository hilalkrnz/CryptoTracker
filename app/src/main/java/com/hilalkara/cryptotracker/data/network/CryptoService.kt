package com.hilalkara.cryptotracker.data.network

import com.hilalkara.cryptotracker.data.dto.CoinDetailResponse
import com.hilalkara.cryptotracker.data.dto.CoinsResponseItem
import com.hilalkara.cryptotracker.utility.CURRENCY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoService {

    companion object {
        const val COINS = "coins"
    }

    @GET("$COINS/markets")
    suspend fun getCryptoCurrencies(@Query("vs_currency") currency: String = String.CURRENCY): List<CoinsResponseItem>

    @GET("$COINS/markets/id")
    suspend fun getCryptoById(@Path("id") id: String): CoinDetailResponse

}