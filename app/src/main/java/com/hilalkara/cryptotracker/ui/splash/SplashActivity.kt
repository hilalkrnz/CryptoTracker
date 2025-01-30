package com.hilalkara.cryptotracker.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkInfo
import com.hilalkara.cryptotracker.MainActivity
import com.hilalkara.cryptotracker.R
import com.hilalkara.cryptotracker.databinding.ActivitySplashBinding
import com.hilalkara.cryptotracker.utility.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySplashBinding::inflate)

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splash)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        viewModel.startWorker()
        observeUiData()
    }

    private fun observeUiData() {
        viewModel.workerState.observe(this) { state ->
            if (state == WorkInfo.State.SUCCEEDED) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}