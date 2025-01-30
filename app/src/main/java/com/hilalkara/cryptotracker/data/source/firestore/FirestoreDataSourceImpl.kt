package com.hilalkara.cryptotracker.data.source.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.hilalkara.cryptotracker.common.NetworkResponseState
import com.hilalkara.cryptotracker.data.dto.CoinsResponseItem
import com.hilalkara.cryptotracker.data.model.CoinFirestoreData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreDataSourceImpl @Inject constructor(
    firestore: FirebaseFirestore
) : FirestoreDataSource {

    private val coinsCollection = firestore.collection("coins")
    private val coinsFavoriteCollection = firestore.collection("favorites")

    override suspend fun saveCoins(coins: List<CoinsResponseItem>) {
        coins.forEach { coin ->
            coinsCollection.document(coin.id ?: return@forEach).set(coin)
                .addOnSuccessListener {
                    Log.d("Firestore", "Success: ${coin.name}")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Failure: ${e.localizedMessage}")
                }
        }
    }

    override suspend fun getAllCoins(): NetworkResponseState<List<CoinFirestoreData>> {
        return try {
            val snapshot = coinsCollection.get().await()
            val coinsList = snapshot.toObjects(CoinFirestoreData::class.java)
            NetworkResponseState.Success(coinsList)
        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }
    }

    override suspend fun getSearchCoins(searchQuery: String): NetworkResponseState<List<CoinFirestoreData>> {
        return try {
            val nameQuery = coinsCollection
                .whereGreaterThanOrEqualTo("name", searchQuery)
                .whereLessThanOrEqualTo("name", searchQuery + "\uf8ff")
                .get()
                .await()

            val symbolQuery = coinsCollection
                .whereGreaterThanOrEqualTo("symbol", searchQuery)
                .whereLessThanOrEqualTo("symbol", searchQuery + "\uf8ff")
                .get()
                .await()

            val nameResults = nameQuery.toObjects(CoinFirestoreData::class.java)
            val symbolResults = symbolQuery.toObjects(CoinFirestoreData::class.java)

            val combinedResults = (nameResults + symbolResults).distinctBy { it.id }

            NetworkResponseState.Success(combinedResults)
        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }
    }

    override suspend fun addFavoriteCoin(coinId: String) {
        try {
            val favoriteCoinRef = coinsFavoriteCollection.document(coinId)
            val data = mapOf("id" to coinId)

            favoriteCoinRef.set(data).await()
            Log.d("FirestoreFavorite", "Add favorite: $coinId")
        } catch (e: Exception) {
            Log.e("FirestoreFavorite", "Error add favorite: ${e.localizedMessage}")
        }
    }

    override suspend fun removeFavoriteCoin(coinId: String) {
        try {
            val favoriteCoinRef = coinsFavoriteCollection.document(coinId)
            favoriteCoinRef.delete().await()
            Log.d("FirestoreFavorite", "Remove favorite: $coinId")
        } catch (e: Exception) {
            Log.e("FirestoreFavorite", "Error remove favorite: ${e.localizedMessage}")
        }
    }

    override suspend fun getFavoriteCoinIds(): NetworkResponseState<List<String>> {
        return try {
            val snapshot = coinsFavoriteCollection.get().await()

            val favoriteIds = snapshot.documents.mapNotNull { it.id }
            NetworkResponseState.Success(favoriteIds)
        } catch (e: Exception) {
            Log.e("FirestoreFavorite", "Error getting favorite IDs: ${e.localizedMessage}")
            NetworkResponseState.Error(e)
        }
    }

    override suspend fun isCoinFavorite(coinId: String): NetworkResponseState<Boolean> {
        return try {
            val favoriteCoinRef = coinsFavoriteCollection.document(coinId)
            val snapshot = favoriteCoinRef.get().await()

            if (snapshot.exists()) {
                NetworkResponseState.Success(true)
            } else {
                NetworkResponseState.Success(false)
            }
        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }
    }


}
