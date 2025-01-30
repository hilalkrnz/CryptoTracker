package com.hilalkara.cryptotracker.utility

import java.text.SimpleDateFormat
import java.util.Locale

const val SERVER_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val SIMPLE_FORMAT_PATTERN = "dd.MM.yyyy, HH:mm:ss"

val String.Companion.CURRENCY: String
    get() = "usd"

val String.Companion.EMPTY: String
    get() = ""

fun String?.orDash(): String = if (this.isNullOrBlank()) " - " else this

fun String?.formatToDateTimeLegacy(
    inputPattern: String = SERVER_TIME_FORMAT,
    outputPattern: String = SIMPLE_FORMAT_PATTERN
): String {
    return try {
        val inputFormatter = SimpleDateFormat(inputPattern, Locale.getDefault())
        val outputFormatter = SimpleDateFormat(outputPattern, Locale.getDefault())

        val parsedDate = inputFormatter.parse(this.orEmpty())
        if (parsedDate != null) {
            outputFormatter.format(parsedDate)
        } else {
            " - "
        }
    } catch (e: Exception) {
        " - "
    }
}
