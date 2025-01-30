package com.hilalkara.cryptotracker.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hilalkara.cryptotracker.R
import com.hilalkara.cryptotracker.databinding.FragmentFavoriteBinding
import com.hilalkara.cryptotracker.domain.model.CoinUiData
import com.hilalkara.cryptotracker.ui.favorite.adapter.FavoriteAdapter
import com.hilalkara.cryptotracker.utility.gone
import com.hilalkara.cryptotracker.utility.viewBinding
import com.hilalkara.cryptotracker.utility.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val binding by viewBinding(FragmentFavoriteBinding::bind)

    private val viewModel by viewModels<FavoriteViewModel>()

    private val favoriteAdapter = FavoriteAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllFavoriteCoins()

        setUpRecyclerView()
        observeUiData()
        setOnClickCoinItem()
    }

    private fun setUpRecyclerView() {
        binding.favoriteRv.adapter = favoriteAdapter
    }

    private fun observeUiData() = with(binding) {
        viewModel.favoriteState.observe(viewLifecycleOwner) { state ->
            when (state) {
                FavoriteState.Loading -> {
                    progressBar.visible()
                }

                is FavoriteState.SuccessState -> {
                    progressBar.gone()
                    tvEmpty.gone()
                    favoriteRv.visible()
                    handleSuccessFavoriteState(state.coins)
                }

                is FavoriteState.EmptyScreen -> {
                    progressBar.gone()
                    favoriteRv.gone()
                    tvEmpty.visible()
                }

                is FavoriteState.ShowPopUp -> {
                    progressBar.gone()
                    Log.e("SearchError", "${state.errorMessage}")
                    Snackbar.make(requireView(), state.errorMessage.orEmpty(), 1000).show()
                }
            }
        }
    }

    private fun handleSuccessFavoriteState(favoriteConList: List<CoinUiData>) {
        favoriteAdapter.updateFavoriteCoinList(favoriteConList)
    }

    private fun setOnClickCoinItem() {
        favoriteAdapter.setOnItemClickListener { position ->
            val coinId = favoriteAdapter.getItem(position).id
            if (coinId != null) {
                val action =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(coinId)
                findNavController().navigate(action)
            }
        }

    }
}