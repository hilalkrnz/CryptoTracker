package com.hilalkara.cryptotracker.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hilalkara.cryptotracker.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Nothing)
    val loginState: StateFlow<LoginState> = _loginState

    init {
        viewModelScope.launch {
            checkUser()
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = authRepository.login(email, password)
            if (result.isSuccess) {
                _loginState.value = LoginState.SuccessLogin
            } else {
                _loginState.value =
                    LoginState.ShowPopUp(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    private fun checkUser() = viewModelScope.launch {
        if (authRepository.isUserLoggedIn()) {
            _loginState.value = LoginState.GoToHome
        }
    }

    fun clearError() {
        _loginState.value = LoginState.Nothing
    }
}

sealed interface LoginState {
    data object Nothing : LoginState
    data object Loading : LoginState
    data object SuccessLogin : LoginState
    data object GoToHome : LoginState
    data class ShowPopUp(val errorMessage: String?) : LoginState
}