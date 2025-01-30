package com.hilalkara.cryptotracker.data.mapper

import com.hilalkara.cryptotracker.data.dto.CoinsResponseItem
import com.hilalkara.cryptotracker.data.model.CoinFirestoreData
import javax.inject.Inject

class CoinFirestoreDataListMapperImpl @Inject constructor() :
    ListMapper<CoinsResponseItem, CoinFirestoreData> {
    override fun map(input: List<CoinsResponseItem>?): List<CoinFirestoreData> {
        return input?.map {
            CoinFirestoreData(
                id = it.id.orEmpty(),
                name = it.name.orEmpty(),
                symbol = it.symbol.orEmpty(),
                image = it.image.orEmpty(),
                currentPrice = it.currentPrice ?: 0.0,
                high24h = it.high24h ?: 0.0,
                low24h = it.low24h ?: 0.0,
                priceChangePercentage24h = it.priceChangePercentage24h ?: 0.0
            )
        } ?: emptyList()
    }
}