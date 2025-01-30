package com.hilalkara.cryptotracker.ui.detail

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.hilalkara.cryptotracker.R
import com.hilalkara.cryptotracker.common.RefreshInterval
import com.hilalkara.cryptotracker.databinding.FragmentDetailBinding
import com.hilalkara.cryptotracker.domain.model.CoinDetailUiData
import com.hilalkara.cryptotracker.utility.formatToDateTimeLegacy
import com.hilalkara.cryptotracker.utility.getRefreshInterval
import com.hilalkara.cryptotracker.utility.gone
import com.hilalkara.cryptotracker.utility.orDash
import com.hilalkara.cryptotracker.utility.setRefreshInterval
import com.hilalkara.cryptotracker.utility.viewBinding
import com.hilalkara.cryptotracker.utility.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val viewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailFragmentArgs>()

    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences(
            "settings",
            Context.MODE_PRIVATE
        )
    }
    private var priceUpdateJob: Job? = null
    private var refreshInterval = RefreshInterval.THIRTY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCoinById(args.coinId)
        viewModel.isCoinFavorite(args.coinId)

        observeUiData()
        setupDropdownMenu()
        startPriceUpdateTimer()
        setOnFavoriteClickListener()
    }

    private fun observeUiData() = with(binding) {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                DetailState.Loading -> {
                    progressBar.visible()
                }

                is DetailState.SuccessState -> {
                    progressBar.gone()
                    handleSuccessDetailState(state.coin)
                }

                is DetailState.ShowPopUp -> {
                    progressBar.gone()
                    Log.e("SearchError", "${state.errorMessage}")
                    Snackbar.make(requireView(), state.errorMessage.orEmpty(), 1000).show()
                }

                DetailState.SuccessFavorite -> setUpFavoriteStatus(true)
                DetailState.RemoveFavorite -> setUpFavoriteStatus(false)
            }

        }
    }

    private fun handleSuccessDetailState(data: CoinDetailUiData) {
        binding.apply {
            Glide.with(requireContext()).load(data.image).into(coinImage)
            coinComponent.setCoinDetailData(data)

            coinHashAlgorithm.text = data.hashingAlgorithm.orDash()
            coinLastUpdated.text = data.lastUpdated.formatToDateTimeLegacy()

            coinDescription.text =
                Html.fromHtml(data.description.orDash(), Html.FROM_HTML_MODE_LEGACY)
            coinDescription.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun setOnFavoriteClickListener() {
        binding.favoriteIcon.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.addCoinFavorite(args.coinId)
            } else {
                viewModel.removeFromFavorite(args.coinId)
            }
        }

    }

    private fun setUpFavoriteStatus(isFavorite: Boolean) {
        binding.favoriteIcon.isChecked = isFavorite
    }


    private fun setupDropdownMenu() {
        val intervals = RefreshInterval.values()
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            intervals.map { "${it.seconds}s" })
        binding.refreshIntervalDropdown.setAdapter(adapter)

        refreshInterval = sharedPreferences.getRefreshInterval()
        binding.refreshIntervalDropdown.setText("${refreshInterval.seconds}s", false)

        binding.refreshIntervalDropdown.setOnClickListener {
            it.post { binding.refreshIntervalDropdown.showDropDown() }
        }

        binding.refreshIntervalDropdown.setOnItemClickListener { _, _, position, _ ->
            refreshInterval = intervals[position]
            sharedPreferences.setRefreshInterval(refreshInterval)
            restartPriceUpdateTimer()
        }
    }

    private fun restartPriceUpdateTimer() {
        priceUpdateJob?.cancel()
        startPriceUpdateTimer()
    }

    private fun startPriceUpdateTimer() {
        priceUpdateJob = lifecycleScope.launch {
            while (isActive) {
                updatePrice()
                delay(refreshInterval.seconds * 1000)
            }
        }
    }

    private fun updatePrice() {
        viewModel.getCoinById(args.coinId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        priceUpdateJob?.cancel()
    }
}