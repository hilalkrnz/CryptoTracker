package com.hilalkara.cryptotracker.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hilalkara.cryptotracker.domain.CryptoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CoinSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: CryptoRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            repository.fetchAndSaveCoins()
            Result.success()
        } catch (e: Exception) {
            Log.e("CoinSyncWorker", "Worker Failure: ${e.localizedMessage}")
            Result.failure()
        }
    }
}
