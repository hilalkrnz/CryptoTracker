package com.hilalkara.cryptotracker.ui.favorite.adapter

import androidx.recyclerview.widget.RecyclerView
import com.hilalkara.cryptotracker.databinding.AdapterCoinItemBinding
import com.hilalkara.cryptotracker.domain.model.CoinUiData

class FavoriteViewHolder(
    private val binding: AdapterCoinItemBinding,
    onItemClickListener: ((Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onItemClickListener?.invoke(adapterPosition)
        }
    }

    fun onBind(data: CoinUiData) {
        binding.coinComponent.setCoinData(data)
    }
}