package com.hilalkara.cryptotracker.di.worker

import androidx.work.WorkerFactory
import com.hilalkara.cryptotracker.data.worker.ChildWorkerFactory
import com.hilalkara.cryptotracker.data.worker.CoinSyncWorker
import com.hilalkara.cryptotracker.data.worker.CoinSyncWorkerFactory
import com.hilalkara.cryptotracker.data.worker.CustomWorkerFactory
import com.hilalkara.cryptotracker.di.annotation.WorkerKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkerModule {

    @Binds
    abstract fun bindWorkerFactory(factory: CustomWorkerFactory): WorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(CoinSyncWorker::class)
    abstract fun bindCoinSyncWorkerFactory(factory: CoinSyncWorkerFactory): ChildWorkerFactory
}
