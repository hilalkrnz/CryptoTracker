package com.hilalkara.cryptotracker.ui.register

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
class RegisterFragment : Fragment() {
    private lateinit var composeView: ComposeView

    private val viewModel by viewModels<RegisterViewModel>()

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
            val registerState by viewModel.registerState.collectAsState()
            RegisterScreen(
                registerState = registerState,
                onRegisterClick = { email, password ->
                    registerAccount(email, password)
                },
                onNavigateToSignIn = { goToSignIn() },
                onNavigateToHome = { goToHome() },
                onDismissError = { viewModel.clearError() }
            )
        }
    }

    private fun registerAccount(email: String, password: String) {
        viewModel.register(email, password)
    }

    private fun goToSignIn() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    private fun goToHome() {
        findNavController().navigate(R.id.registerToMainGraph)
    }
}