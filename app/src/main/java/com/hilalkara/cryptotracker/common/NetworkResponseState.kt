package com.hilalkara.cryptotracker.common

sealed interface NetworkResponseState<out T : Any> {
    data object Loading : NetworkResponseState<Nothing>
    data class Success<out T : Any>(val result: T) : NetworkResponseState<T>
    data class Error(val exception: Exception) : NetworkResponseState<Nothing>
}