package com.hilalkara.cryptotracker.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.hilalkara.cryptotracker.data.worker.CoinSyncWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val workManager: WorkManager
) : ViewModel() {

    private val _workerState = MutableLiveData<WorkInfo.State>()
    val workerState: LiveData<WorkInfo.State> get() = _workerState

    fun startWorker() {
        val workRequest = OneTimeWorkRequestBuilder<CoinSyncWorker>().build()

        workManager.enqueueUniqueWork(
            "CoinSyncWork",
            ExistingWorkPolicy.KEEP,
            workRequest
        )

        workManager.getWorkInfosForUniqueWorkLiveData("CoinSyncWork")
            .observeForever { workInfos ->
                workInfos?.forEach { workInfo ->
                    _workerState.postValue(workInfo.state)
                }
            }
    }
}