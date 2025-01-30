package com.hilalkara.cryptotracker.ui.profile

import androidx.lifecycle.ViewModel
import com.hilalkara.cryptotracker.domain.AuthRepository
import com.hilalkara.cryptotracker.domain.model.UserInfoUiData
import com.hilalkara.cryptotracker.utility.SIMPLE_YEAR_FORMAT_PATTERN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profileState: StateFlow<ProfileState> = _profileState

    init {
        getCurrentUserInfo()
    }

    fun getCurrentUserInfo() {
        val userInfo = authRepository.getCurrentUser()
        _profileState.value = ProfileState.SuccessState(
            UserInfoUiData(
                id = userInfo?.uid.orEmpty(),
                email = userInfo?.email.orEmpty(),
                registrationDate = userInfo?.metadata?.creationTimestamp.let { timestamp ->
                    timestamp?.let { Date(it) }?.let {
                        SimpleDateFormat(SIMPLE_YEAR_FORMAT_PATTERN, Locale.getDefault()).format(it)
                    }
                } ?: "Unknown Date"
            )
        )
    }

    fun logOut() {
        authRepository.logout()
        _profileState.value = ProfileState.GoToSignIn
    }

    fun clearError() {
        _profileState.value = ProfileState.Nothing
    }
}

sealed interface ProfileState {
    data object Nothing : ProfileState
    data object Loading : ProfileState
    data object GoToSignIn : ProfileState
    data class SuccessState(val userInfo: UserInfoUiData) : ProfileState
    data class ShowPopUp(val errorMessage: String) : ProfileState
}