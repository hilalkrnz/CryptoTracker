package com.hilalkara.cryptotracker.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hilalkara.cryptotracker.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Nothing)
    val registerState: StateFlow<RegisterState> = _registerState

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            val result = authRepository.register(email, password)
            _registerState.value = if (result.isSuccess) {
                RegisterState.SuccessRegister
            } else {
                RegisterState.ShowPopUp(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun clearError() {
        _registerState.value = RegisterState.Nothing
    }
}

sealed interface RegisterState {
    data object Nothing : RegisterState
    data object Loading : RegisterState
    data object SuccessRegister : RegisterState
    data class ShowPopUp(val errorMessage: String?) : RegisterState
}