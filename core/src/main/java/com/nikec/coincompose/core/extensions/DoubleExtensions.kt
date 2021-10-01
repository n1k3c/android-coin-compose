package com.nikec.coincompose.core.extensions

import com.nikec.coincompose.core.data.model.Currency
import java.text.NumberFormat
import java.util.*

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

fun Double.formatToStringWithCurrency(currency: Currency): String =
    currency.symbol + NumberFormat.getInstance(Locale.getDefault())
        .format(this)
