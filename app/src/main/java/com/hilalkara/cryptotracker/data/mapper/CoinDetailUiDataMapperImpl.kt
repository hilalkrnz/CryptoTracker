package com.hilalkara.cryptotracker.data.mapper

import com.hilalkara.cryptotracker.data.dto.CoinDetailResponse
import com.hilalkara.cryptotracker.domain.model.CoinDetailUiData
import javax.inject.Inject

class CoinDetailUiDataMapperImpl @Inject constructor() :
    Mapper<CoinDetailResponse, CoinDetailUiData> {
    override fun map(input: CoinDetailResponse?): CoinDetailUiData {
        return CoinDetailUiData(
            id = input?.id.orEmpty(),
            symbol = input?.symbol.orEmpty(),
            name = input?.name.orEmpty(),
            hashingAlgorithm = input?.hashingAlgorithm.orEmpty(),
            description = input?.description?.en.orEmpty(),
            image = input?.image?.large.orEmpty(),
            currentPrice = "$ ${input?.marketData?.currentPrice?.usd}",
            priceChangePercentage24h = input?.marketData?.priceChangePercentage24h ?: 0.0,
            lastUpdated = input?.lastUpdated.orEmpty()
        )
    }
}