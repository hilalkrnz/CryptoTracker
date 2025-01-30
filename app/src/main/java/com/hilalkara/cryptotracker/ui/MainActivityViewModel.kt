package com.hilalkara.cryptotracker.ui

import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hilalkara.cryptotracker.data.worker.CoinSyncWorker
import com.hilalkara.cryptotracker.data.worker.PriceChangeWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val workManager: WorkManager
) : ViewModel() {

    fun schedulePeriodicCoinSync() {
        val workRequest = PeriodicWorkRequestBuilder<CoinSyncWorker>(
            10, TimeUnit.MINUTES
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).build()

        workManager.enqueueUniquePeriodicWork(
            "PeriodicCoinSyncWork",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    fun schedulePriceChangeWorker() {
        val workRequest = PeriodicWorkRequestBuilder<PriceChangeWorker>(
            5, TimeUnit.SECONDS
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).build()

        workManager.enqueueUniquePeriodicWork(
            "PriceChangeWork",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}