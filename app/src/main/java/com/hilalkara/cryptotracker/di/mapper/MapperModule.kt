package com.hilalkara.cryptotracker.di.mapper

import com.hilalkara.cryptotracker.data.dto.CoinDetailResponse
import com.hilalkara.cryptotracker.data.dto.CoinsResponseItem
import com.hilalkara.cryptotracker.data.mapper.CoinDetailUiDataMapperImpl
import com.hilalkara.cryptotracker.data.mapper.CoinFirestoreDataListMapperImpl
import com.hilalkara.cryptotracker.data.mapper.CoinUiDataListMapperImpl
import com.hilalkara.cryptotracker.data.mapper.ListMapper
import com.hilalkara.cryptotracker.data.mapper.Mapper
import com.hilalkara.cryptotracker.data.model.CoinFirestoreData
import com.hilalkara.cryptotracker.domain.model.CoinDetailUiData
import com.hilalkara.cryptotracker.domain.model.CoinUiData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    @Singleton
    abstract fun bindCoinUiDataListMapper(
        coinUiDataListMapperImpl: CoinUiDataListMapperImpl
    ): ListMapper<CoinFirestoreData, CoinUiData>

    @Binds
    @Singleton
    abstract fun bindCoinDetailUiDataMapper(
        coinDetailMapperImpl: CoinDetailUiDataMapperImpl
    ): Mapper<CoinDetailResponse, CoinDetailUiData>

    @Binds
    @Singleton
    abstract fun bindCoinFirestoreDataListMapper(
        coinFirestoreDataListMapperImpl: CoinFirestoreDataListMapperImpl
    ): ListMapper<CoinsResponseItem, CoinFirestoreData>
}