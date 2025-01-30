package com.hilalkara.cryptotracker.ui.favorite

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
class FavoriteViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    private val _favoriteState = MutableLiveData<FavoriteState>()
    val favoriteState: LiveData<FavoriteState> get() = _favoriteState

    fun getAllFavoriteCoins() {
        viewModelScope.launch {
            repository.getFavoriteCoins().collectLatest { response ->
                _favoriteState.value = when (response) {
                    is NetworkResponseState.Loading -> FavoriteState.Loading
                    is NetworkResponseState.Success -> {
                        if (response.result.isNullOrEmpty()) {
                            FavoriteState.EmptyScreen
                        } else {
                            FavoriteState.SuccessState(response.result)
                        }
                    }

                    is NetworkResponseState.Error -> FavoriteState.ShowPopUp(response.exception.message)
                }
            }
        }
    }

}

sealed interface FavoriteState {
    data object Loading : FavoriteState
    data object EmptyScreen : FavoriteState
    data class SuccessState(val coins: List<CoinUiData>) : FavoriteState
    data class ShowPopUp(val errorMessage: String?) : FavoriteState
}