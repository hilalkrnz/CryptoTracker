package com.hilalkara.cryptotracker.data.source.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.hilalkara.cryptotracker.data.dto.CoinsResponseItem
import javax.inject.Inject

class FirestoreDataSourceImpl @Inject constructor(
    firestore: FirebaseFirestore
) : FirestoreDataSource {

    private val coinsCollection = firestore.collection("coins")

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
}
