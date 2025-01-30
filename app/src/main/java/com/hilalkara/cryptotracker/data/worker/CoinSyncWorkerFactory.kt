package com.hilalkara.cryptotracker.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.hilalkara.cryptotracker.domain.CryptoRepository
import javax.inject.Inject

class CoinSyncWorkerFactory @Inject constructor(
    private val repository: CryptoRepository
) : ChildWorkerFactory {
    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        return CoinSyncWorker(appContext, params, repository)
    }
}