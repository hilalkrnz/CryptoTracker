package com.hilalkara.cryptotracker.di.repository

import com.hilalkara.cryptotracker.data.repository.AuthRepositoryImpl
import com.hilalkara.cryptotracker.data.repository.CryptoRepositoryImpl
import com.hilalkara.cryptotracker.domain.AuthRepository
import com.hilalkara.cryptotracker.domain.CryptoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSerenityRepository(
        repositoryImpl: CryptoRepositoryImpl
    ): CryptoRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}