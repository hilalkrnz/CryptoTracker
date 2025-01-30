package com.hilalkara.cryptotracker.domain

import com.hilalkara.cryptotracker.common.NetworkResponseState
import com.hilalkara.cryptotracker.domain.model.CoinDetailUiData
import com.hilalkara.cryptotracker.domain.model.CoinUiData
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {

    suspend fun fetchAndSaveCoins()
    suspend fun getAllCoins(): Flow<NetworkResponseState<List<CoinUiData>>>
    suspend fun getCoinById(id: String): Flow<NetworkResponseState<CoinDetailUiData>>
    suspend fun getSearchCoins(searchQuery: String): Flow<NetworkResponseState<List<CoinUiData>>>
    suspend fun addFavoriteCoin(coinId: String)
    suspend fun removeFavoriteCoin(coinId: String)
    suspend fun getFavoriteCoins(): Flow<NetworkResponseState<List<CoinUiData>>>
    suspend fun isCoinFavorite(coinId: String): NetworkResponseState<Boolean>
}