package com.hilalkara.cryptotracker.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hilalkara.cryptotracker.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var composeView: ComposeView

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        composeView.setContent {
            val profileState by viewModel.profileState.collectAsState()
            ProfileScreen(
                profileState = profileState,
                onLogoutClick = { viewModel.logOut() },
                onGoToSignIn = { goToLogin() },
                onDismissError = { viewModel.clearError() }
            )
        }
    }

    private fun goToLogin() {
        findNavController().navigate(R.id.loginFragment)
    }

}