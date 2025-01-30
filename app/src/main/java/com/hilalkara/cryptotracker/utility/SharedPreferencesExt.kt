package com.hilalkara.cryptotracker.utility

import android.content.SharedPreferences
import com.hilalkara.cryptotracker.common.RefreshInterval

fun SharedPreferences.getRefreshInterval(): RefreshInterval {
    val savedValue = getLong("refresh_interval", 30)
    return RefreshInterval.fromSeconds(savedValue)
}

fun SharedPreferences.setRefreshInterval(interval: RefreshInterval) {
    edit().putLong("refresh_interval", interval.seconds).apply()
}