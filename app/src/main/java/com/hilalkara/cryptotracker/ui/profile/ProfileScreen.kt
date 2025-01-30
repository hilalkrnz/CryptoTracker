package com.hilalkara.cryptotracker.ui.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hilalkara.cryptotracker.common.component.ErrorDialog

@Composable
fun ProfileScreen(
    profileState: ProfileState,
    onLogoutClick: () -> Unit,
    onGoToSignIn: () -> Unit,
    onDismissError: () -> Unit
) {
    when (profileState) {
        is ProfileState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ProfileState.SuccessState -> {
            ProfileContent(
                userInfo = profileState.userInfo,
                onLogoutClick = onLogoutClick
            )
        }

        is ProfileState.ShowPopUp -> {
            val errorMessage = profileState.errorMessage
            ErrorDialog(
                message = errorMessage,
                onDismiss = { onDismissError() }
            )
        }

        is ProfileState.GoToSignIn -> {
            onGoToSignIn()
        }

        else -> {}
    }
}