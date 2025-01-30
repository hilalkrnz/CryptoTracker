package com.hilalkara.cryptotracker.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hilalkara.cryptotracker.common.NetworkResponseState
import com.hilalkara.cryptotracker.domain.CryptoRepository
import com.hilalkara.cryptotracker.domain.model.CoinUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    private val _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState> get() = _searchState

    fun getAllCoins() {
        viewModelScope.launch {
            repository.getAllCoins().collectLatest { response ->
                _searchState.value = when (response) {
                    is NetworkResponseState.Loading -> SearchState.Loading
                    is NetworkResponseState.Success -> {
                        if (response.result.isNullOrEmpty()) {
                            SearchState.EmptyScreen
                        } else {
                            SearchState.SuccessState(response.result)
                        }
                    }

                    is NetworkResponseState.Error -> SearchState.ShowPopUp(response.exception.message)
                }
            }
        }
    }

    fun searchCoins(searchQuery: String) {
        viewModelScope.launch {
            repository.getSearchCoins(searchQuery).collectLatest { response ->
                _searchState.value = when (response) {
                    is NetworkResponseState.Loading -> SearchState.Loading
                    is NetworkResponseState.Success -> {
                        if (response.result.isNullOrEmpty()) {
                            SearchState.EmptyScreen
                        } else {
                            SearchState.SuccessState(response.result)
                        }
                    }

                    is NetworkResponseState.Error -> SearchState.ShowPopUp(response.exception.message)
                }
            }
        }
    }

}

sealed interface SearchState {
    data object Loading : SearchState
    data object EmptyScreen : SearchState
    data class SuccessState(val coins: List<CoinUiData>) : SearchState
    data class ShowPopUp(val errorMessage: String?) : SearchState
}