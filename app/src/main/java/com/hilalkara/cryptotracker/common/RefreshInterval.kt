package com.hilalkara.cryptotracker.common

enum class RefreshInterval(val seconds: Long) {
    FIVE(5), TEN(10), TWENTY(20), THIRTY(30);

    companion object {
        fun fromSeconds(value: Long) = values().find { it.seconds == value } ?: THIRTY
    }
}