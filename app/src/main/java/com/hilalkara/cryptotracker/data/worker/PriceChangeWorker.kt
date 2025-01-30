package com.hilalkara.cryptotracker.data.worker

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hilalkara.cryptotracker.R
import com.hilalkara.cryptotracker.common.NetworkResponseState
import com.hilalkara.cryptotracker.domain.CryptoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull

@HiltWorker
class PriceChangeWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: CryptoRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val favoriteCoins = repository.getFavoriteCoins().firstOrNull()
            if (favoriteCoins is NetworkResponseState.Success) {
                for (coin in favoriteCoins.result) {
                    val newPrice = coin.currentPrice
                    val oldPrice = getSavedPrice(coin.id)

                    if (oldPrice != null && oldPrice != newPrice) {
                        showNotification(coin.name, oldPrice, newPrice)
                        saveNewPrice(coin.id, newPrice)
                    }
                }
            }
            Result.success()
        } catch (e: Exception) {
            Log.e("PriceChangeWorker", "Worker Failure: ${e.localizedMessage}")
            Result.retry()
        }
    }

    private fun getSavedPrice(coinId: String): String? {
        val sharedPreferences =
            applicationContext.getSharedPreferences("CoinPrices", Context.MODE_PRIVATE)
        return sharedPreferences.getString(coinId, null)
    }

    private fun saveNewPrice(coinId: String, newPrice: String) {
        val sharedPreferences =
            applicationContext.getSharedPreferences("CoinPrices", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(coinId, newPrice).apply()
    }

    private fun showNotification(coinName: String, oldPrice: String, newPrice: String) {
        val notificationManager =
            ContextCompat.getSystemService(applicationContext, NotificationManager::class.java)
        val builder = NotificationCompat.Builder(applicationContext, "PRICE_CHANGE_CHANNEL")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("$coinName Price Changed!")
            .setContentText("Old: $oldPrice, New: $newPrice")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager?.notify(coinName.hashCode(), builder.build())
    }
}
