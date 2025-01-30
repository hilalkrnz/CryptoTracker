package com.hilalkara.cryptotracker.ui.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hilalkara.cryptotracker.databinding.AdapterCoinItemBinding
import com.hilalkara.cryptotracker.domain.model.CoinUiData
import com.hilalkara.cryptotracker.utility.inflateAdapterItem

class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {

    private val coinList = mutableListOf<CoinUiData>()

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(itemClickListener: ((Int) -> Unit)?) {
        this.onItemClickListener = itemClickListener
    }

    fun updateCoinList(newList: List<CoinUiData>) {
        coinList.apply {
            clear()
            addAll(newList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            parent.inflateAdapterItem(AdapterCoinItemBinding::inflate),
            onItemClickListener
        )
    }

    fun getItem(position: Int): CoinUiData = coinList[position]

    override fun getItemCount(): Int = coinList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = coinList[position]
        holder.onBind(item)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(coinList.indexOf(item))
        }
    }
}