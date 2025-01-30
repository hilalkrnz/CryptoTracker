package com.hilalkara.cryptotracker.data.mapper

import com.hilalkara.cryptotracker.data.model.CoinFirestoreData
import com.hilalkara.cryptotracker.domain.model.CoinUiData
import javax.inject.Inject

class CoinUiDataListMapperImpl @Inject constructor() : ListMapper<CoinFirestoreData, CoinUiData> {
    override fun map(input: List<CoinFirestoreData>?): List<CoinUiData> {
        return input?.map {
            CoinUiData(
                id = it.id,
                name = it.name,
                symbol = it.symbol,
                image = it.image,
                currentPrice = "$ ${it.currentPrice}",
                high24h = it.high24h,
                low24h = it.low24h,
                priceChangePercentage24h = it.priceChangePercentage24h
            )
        } ?: emptyList()
    }
}