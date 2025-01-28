package com.hilalkara.cryptotracker.data.source

import com.hilalkara.cryptotracker.data.NetworkResponseState
import com.hilalkara.cryptotracker.data.dto.CoinDetailResponse
import com.hilalkara.cryptotracker.data.dto.CoinsResponseItem

interface RemoteDataSource {
    suspend fun getCryptoCurrencies() : NetworkResponseState<List<CoinsResponseItem>>
    suspend fun getCryptoById(id: String) : NetworkResponseState<CoinDetailResponse>
}
