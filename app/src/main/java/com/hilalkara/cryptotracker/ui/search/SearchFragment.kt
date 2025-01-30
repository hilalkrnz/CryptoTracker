package com.hilalkara.cryptotracker.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hilalkara.cryptotracker.R
import com.hilalkara.cryptotracker.databinding.FragmentSearchBinding
import com.hilalkara.cryptotracker.domain.model.CoinUiData
import com.hilalkara.cryptotracker.ui.search.adapter.SearchAdapter
import com.hilalkara.cryptotracker.utility.gone
import com.hilalkara.cryptotracker.utility.observeTextChanges
import com.hilalkara.cryptotracker.utility.viewBinding
import com.hilalkara.cryptotracker.utility.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<SearchViewModel>()

    private val searchAdapter = SearchAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllCoins()

        setUpRecyclerView()
        observeUiData()
        observeSearchTextChanges()
        setOnClickCoinItem()
    }

    private fun setUpRecyclerView() {
        binding.searchRv.adapter = searchAdapter
    }

    private fun observeUiData() = with(binding) {
        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SearchState.Loading -> {
                    progressBar.visible()
                }

                is SearchState.SuccessState -> {
                    progressBar.gone()
                    tvEmpty.gone()
                    searchRv.visible()
                    handleSuccessSearchState(state.coins)
                }

                is SearchState.EmptyScreen -> {
                    progressBar.gone()
                    searchRv.gone()
                    tvEmpty.visible()
                }

                is SearchState.ShowPopUp -> {
                    progressBar.gone()
                    Log.e("SearchError", "${state.errorMessage}")
                    Snackbar.make(requireView(), state.errorMessage.orEmpty(), 1000).show()
                }
            }

        }
    }

    private fun handleSuccessSearchState(data: List<CoinUiData>) {
        searchAdapter.updateCoinList(data)
    }

    private fun observeSearchTextChanges() {
        binding.searchEditText.observeTextChanges()
            .debounce(SEARCH_DEBOUNCE_TIME_IN_MILLISECONDS)
            .onEach { searchText ->
                if (searchText.length >= MINIMUM_SEARCH_LENGTH) {
                    viewModel.searchCoins(searchText)
                } else {
                    viewModel.getAllCoins()
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun setOnClickCoinItem() {
        searchAdapter.setOnItemClickListener { position ->
            val coinId = searchAdapter.getItem(position).id
            if (coinId != null) {
                val action =
                    SearchFragmentDirections.actionSearchFragmentToDetailFragment(coinId)
                findNavController().navigate(action)
            }
        }
    }

    companion object {
        private const val MINIMUM_SEARCH_LENGTH = 1
        private const val SEARCH_DEBOUNCE_TIME_IN_MILLISECONDS = 200L
    }

}