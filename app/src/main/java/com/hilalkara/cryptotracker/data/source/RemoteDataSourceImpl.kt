package com.hilalkara.cryptotracker.data.source

import com.hilalkara.cryptotracker.data.NetworkResponseState
import com.hilalkara.cryptotracker.data.dto.CoinDetailResponse
import com.hilalkara.cryptotracker.data.dto.CoinsResponseItem
import com.hilalkara.cryptotracker.data.network.CryptoService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val cryptoService: CryptoService) :
    RemoteDataSource {
    override suspend fun getCryptoCurrencies(): NetworkResponseState<List<CoinsResponseItem>> =
        try {
            val response = cryptoService.getCryptoCurrencies()
            NetworkResponseState.Success(response)
        }catch (e: Exception) {
            NetworkResponseState.Error(e)
        }

    override suspend fun getCryptoById(id: String): NetworkResponseState<CoinDetailResponse> =
        try {
            val response = cryptoService.getCryptoById(id)
            NetworkResponseState.Success(response)
        }catch (e: Exception) {
            NetworkResponseState.Error(e)
        }

}