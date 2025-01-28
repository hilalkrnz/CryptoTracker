package com.hilalkara.cryptotracker.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.hilalkara.cryptotracker.data.source.RemoteDataSource
import com.hilalkara.cryptotracker.data.source.firestore.FirestoreDataSource
import javax.inject.Inject

class CoinSyncWorkerFactory @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val firestoreDataSource: FirestoreDataSource
) : ChildWorkerFactory {
    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        return CoinSyncWorker(appContext, params, remoteDataSource, firestoreDataSource)
    }
}