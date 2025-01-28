package com.hilalkara.cryptotracker.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hilalkara.cryptotracker.data.NetworkResponseState
import com.hilalkara.cryptotracker.data.source.RemoteDataSource
import com.hilalkara.cryptotracker.data.source.firestore.FirestoreDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CoinSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val remoteDataSource: RemoteDataSource,
    private val firestoreDataSource: FirestoreDataSource
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            when (val response = remoteDataSource.getCryptoCurrencies()) {
                is NetworkResponseState.Success -> {
                    firestoreDataSource.saveCoins(response.result)
                    Result.success()
                }

                is NetworkResponseState.Error -> {
                    Log.e("CoinSyncWorker", "API Error: ${response.exception.localizedMessage}")
                    Result.retry()
                }

                else -> {
                    Log.e("CoinSyncWorker", "Unexpected state.")
                    Result.failure()
                }
            }
        } catch (e: Exception) {
            Log.e("CoinSyncWorker", "Worker Error: ${e.localizedMessage}")
            Result.failure()
        }
    }
}
