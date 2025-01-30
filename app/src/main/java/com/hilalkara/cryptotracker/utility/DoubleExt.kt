package com.hilalkara.cryptotracker.utility

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun Double.toPercentageString(): String {
    return String.format("%.2f%%", this)
}