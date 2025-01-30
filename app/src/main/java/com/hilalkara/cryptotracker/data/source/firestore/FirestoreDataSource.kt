package com.hilalkara.cryptotracker.data.source.firestore

import com.hilalkara.cryptotracker.common.NetworkResponseState
import com.hilalkara.cryptotracker.data.dto.CoinsResponseItem
import com.hilalkara.cryptotracker.data.model.CoinFirestoreData

interface FirestoreDataSource {

    suspend fun saveCoins(coins: List<CoinsResponseItem>)
    suspend fun getAllCoins(): NetworkResponseState<List<CoinFirestoreData>>
    suspend fun getSearchCoins(searchQuery: String): NetworkResponseState<List<CoinFirestoreData>>
    suspend fun addFavoriteCoin(coinId: String)
    suspend fun removeFavoriteCoin(coinId: String)
    suspend fun getFavoriteCoinIds(): NetworkResponseState<List<String>>
    suspend fun isCoinFavorite(coinId: String): NetworkResponseState<Boolean>
}