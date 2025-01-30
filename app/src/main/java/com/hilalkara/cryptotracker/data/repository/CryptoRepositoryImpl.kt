package com.hilalkara.cryptotracker.data.repository

import android.util.Log
import androidx.work.ListenableWorker.Result
import com.hilalkara.cryptotracker.common.NetworkResponseState
import com.hilalkara.cryptotracker.data.dto.CoinDetailResponse
import com.hilalkara.cryptotracker.data.dto.CoinsResponseItem
import com.hilalkara.cryptotracker.data.mapper.CoinDetailUiDataMapperImpl
import com.hilalkara.cryptotracker.data.mapper.CoinFirestoreDataListMapperImpl
import com.hilalkara.cryptotracker.data.mapper.CoinUiDataListMapperImpl
import com.hilalkara.cryptotracker.data.model.CoinFirestoreData
import com.hilalkara.cryptotracker.data.source.RemoteDataSource
import com.hilalkara.cryptotracker.data.source.firestore.FirestoreDataSource
import com.hilalkara.cryptotracker.domain.CryptoRepository
import com.hilalkara.cryptotracker.domain.model.CoinDetailUiData
import com.hilalkara.cryptotracker.domain.model.CoinUiData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val firestoreDataSource: FirestoreDataSource,
    private val coinUiDataListMapper: CoinUiDataListMapperImpl,
    private val coinFirestoreDataListMapper: CoinFirestoreDataListMapperImpl,
    private val coinDetailUiDataMapper: CoinDetailUiDataMapperImpl
) : CryptoRepository {
    override suspend fun fetchAndSaveCoins() {
        when (val response = remoteDataSource.getCryptoCurrencies()) {
            is NetworkResponseState.Success -> {
                firestoreDataSource.saveCoins(response.result)
            }

            is NetworkResponseState.Error -> {
                Log.e("CoinRepository", "API Error: ${response.exception.localizedMessage}")
            }

            else -> {
                Log.e("CoinSyncWorker", "Unexpected state.")
                Result.failure()
            }
        }
    }

    override suspend fun getAllCoins(): Flow<NetworkResponseState<List<CoinUiData>>> {
        return flow {
            emit(NetworkResponseState.Loading)

            when (val response = firestoreDataSource.getAllCoins()) {
                is NetworkResponseState.Success -> {
                    val uiData = mapCoinList(response.result)
                    emit(NetworkResponseState.Success(uiData))
                }

                is NetworkResponseState.Error -> {
                    emit(NetworkResponseState.Error(response.exception))
                }

                else -> {}
            }
        }
    }

    override suspend fun getCoinById(id: String): Flow<NetworkResponseState<CoinDetailUiData>> {
        return flow {
            emit(NetworkResponseState.Loading)
            when (val response = remoteDataSource.getCryptoById(id)) {
                is NetworkResponseState.Success -> {
                    val uiData = mapCoinDetail(response.result)
                    emit(NetworkResponseState.Success(uiData))
                }

                is NetworkResponseState.Error -> {
                    emit(NetworkResponseState.Error(response.exception))
                }

                else -> {}
            }
        }
    }

    override suspend fun getSearchCoins(searchCoin: String): Flow<NetworkResponseState<List<CoinUiData>>> {
        return flow {
            emit(NetworkResponseState.Loading)

            when (val response = firestoreDataSource.getSearchCoins(searchCoin)) {
                is NetworkResponseState.Success -> {
                    val uiData = mapCoinList(response.result)
                    emit(NetworkResponseState.Success(uiData))
                }

                is NetworkResponseState.Error -> {
                    emit(NetworkResponseState.Error(response.exception))
                }

                else -> {}
            }
        }
    }

    override suspend fun addFavoriteCoin(coinId: String) {
        firestoreDataSource.addFavoriteCoin(coinId)
    }

    override suspend fun removeFavoriteCoin(coinId: String) {
        firestoreDataSource.removeFavoriteCoin(coinId)
    }

    override suspend fun getFavoriteCoins(): Flow<NetworkResponseState<List<CoinUiData>>> {
        return flow {
            emit(NetworkResponseState.Loading)

            val favoriteIdsResult = firestoreDataSource.getFavoriteCoinIds()
            val ids = if (favoriteIdsResult is NetworkResponseState.Success) {
                val favoriteIds = favoriteIdsResult.result
                favoriteIds.joinToString(",")
            } else {
                ""
            }

            if (ids.isEmpty()) {
                emit(NetworkResponseState.Success(emptyList()))
                return@flow
            }

            when (val response = remoteDataSource.getCryptoCurrencies(ids = ids)) {
                is NetworkResponseState.Success -> {
                    val uiData = mapCoinList(mapCoinFirestoreList(response.result))
                    emit(NetworkResponseState.Success(uiData))
                }

                is NetworkResponseState.Error -> {
                    emit(NetworkResponseState.Error(response.exception))
                }

                else -> {}
            }
        }
    }


    override suspend fun isCoinFavorite(coinId: String): NetworkResponseState<Boolean> {
        return when (val response = firestoreDataSource.isCoinFavorite(coinId)) {
            is NetworkResponseState.Success -> {
                NetworkResponseState.Success(response.result)
            }

            is NetworkResponseState.Error -> {
                NetworkResponseState.Error(response.exception)
            }

            else -> {
                NetworkResponseState.Success(false)
            }
        }
    }

    private fun mapCoinList(mapCoinList: List<CoinFirestoreData>?): List<CoinUiData> {
        return coinUiDataListMapper.map(mapCoinList)
    }

    private fun mapCoinFirestoreList(mapCoinList: List<CoinsResponseItem>?): List<CoinFirestoreData> {
        return coinFirestoreDataListMapper.map(mapCoinList)
    }

    private fun mapCoinDetail(mapCoinDetail: CoinDetailResponse?): CoinDetailUiData {
        return coinDetailUiDataMapper.map(mapCoinDetail)
    }

}