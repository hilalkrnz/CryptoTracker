package com.hilalkara.cryptotracker.data.source.firestore

import com.hilalkara.cryptotracker.data.dto.CoinsResponseItem

interface FirestoreDataSource {

    suspend fun saveCoins(coins: List<CoinsResponseItem>)
}