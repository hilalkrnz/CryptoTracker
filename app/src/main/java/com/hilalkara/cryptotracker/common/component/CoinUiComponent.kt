package com.hilalkara.cryptotracker.common.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.hilalkara.cryptotracker.R
import com.hilalkara.cryptotracker.databinding.LayoutCoinBinding
import com.hilalkara.cryptotracker.domain.model.CoinDetailUiData
import com.hilalkara.cryptotracker.domain.model.CoinUiData
import com.hilalkara.cryptotracker.utility.gone
import com.hilalkara.cryptotracker.utility.toPercentageString

class CoinUiComponent @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAtrr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAtrr) {
    private val binding = LayoutCoinBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        addView(binding.root)
    }

    fun setCoinData(data: CoinUiData) {
        binding.apply {
            Glide.with(context).load(data.image).into(coinImage)
            coinName.text = data.name
            coinSymbol.text = data.symbol
            coinPrice.text = data.currentPrice
            coinChangePercentage.text = data.priceChangePercentage24h.toPercentageString()
            setChangePercentageColor(data.priceChangePercentage24h)
        }
    }

    fun setCoinDetailData(data: CoinDetailUiData) {
        binding.apply {
            coinImage.gone()
            coinName.text = data.name
            coinSymbol.text = data.symbol
            coinPrice.text = data.currentPrice
            coinChangePercentage.text = data.priceChangePercentage24h.toPercentageString()
            setChangePercentageColor(data.priceChangePercentage24h)
        }
    }

    private fun setChangePercentageColor(changePercentage: Double) {
        val color = if (changePercentage >= 0) {
            ContextCompat.getColor(context, R.color.green)
        } else {
            ContextCompat.getColor(context, R.color.red)
        }
        binding.coinChangePercentage.setTextColor(color)
    }

}