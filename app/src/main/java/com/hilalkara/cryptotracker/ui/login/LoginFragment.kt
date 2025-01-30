package com.hilalkara.cryptotracker.ui.login

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
import com.hilalkara.cryptotracker.ui.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var composeView: ComposeView

    private val viewModel by viewModels<LoginViewModel>()

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
            val loginState by viewModel.loginState.collectAsState()
            LoginScreen(
                loginState = loginState,
                onLoginClick = { email, password ->
                    loginAccount(email, password)
                },
                onNavigateToRegister = { goToRegister() },
                onNavigateToHome = { goToHome() },
                onDismissError = { viewModel.clearError() }
            )
        }
    }

    private fun loginAccount(email: String, password: String) {
        viewModel.login(email, password)
    }

    private fun goToRegister() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    private fun goToHome() {
        findNavController().navigate(R.id.loginToMainGraph)
    }
}