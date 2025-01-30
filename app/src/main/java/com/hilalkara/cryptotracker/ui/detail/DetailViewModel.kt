package com.hilalkara.cryptotracker.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hilalkara.cryptotracker.common.NetworkResponseState
import com.hilalkara.cryptotracker.domain.CryptoRepository
import com.hilalkara.cryptotracker.domain.model.CoinDetailUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    private val _detailUiState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState> get() = _detailUiState

    fun getCoinById(coinId: String) {
        viewModelScope.launch {
            repository.getCoinById(coinId).collectLatest { response ->
                _detailUiState.value = when (response) {
                    is NetworkResponseState.Loading -> DetailState.Loading
                    is NetworkResponseState.Success -> DetailState.SuccessState(response.result)
                    is NetworkResponseState.Error -> DetailState.ShowPopUp(response.exception.message)
                }
            }
        }

    }

    fun removeFromFavorite(iconId: String) {
        viewModelScope.launch {
            repository.removeFavoriteCoin(iconId)
            _detailUiState.value = DetailState.RemoveFavorite
        }
    }

    fun addCoinFavorite(iconId: String) {
        viewModelScope.launch {
            repository.addFavoriteCoin(iconId)
            _detailUiState.value = DetailState.SuccessFavorite
        }
    }

    fun isCoinFavorite(coinId: String) {
        viewModelScope.launch {
            val response = repository.isCoinFavorite(coinId)
            _detailUiState.value = when (response) {
                is NetworkResponseState.Loading -> DetailState.Loading
                is NetworkResponseState.Success -> {
                    if (response.result) DetailState.SuccessFavorite else DetailState.RemoveFavorite
                }

                is NetworkResponseState.Error -> DetailState.ShowPopUp(response.exception.message)
            }
        }
    }
}

sealed interface DetailState {
    data object Loading : DetailState
    data object SuccessFavorite : DetailState
    data object RemoveFavorite : DetailState
    data class SuccessState(val coin: CoinDetailUiData) : DetailState
    data class ShowPopUp(val errorMessage: String?) : DetailState
}