package com.hilalkara.cryptotracker.di.source

import com.hilalkara.cryptotracker.data.source.RemoteDataSource
import com.hilalkara.cryptotracker.data.source.RemoteDataSourceImpl
import com.hilalkara.cryptotracker.data.source.firestore.FirestoreDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindFirestoreDataSource(
        firestoreDataSource: FirestoreDataSourceImpl
    ): RemoteDataSource
}