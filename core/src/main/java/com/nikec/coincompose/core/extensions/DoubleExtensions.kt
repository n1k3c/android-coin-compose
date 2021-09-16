package com.nikec.coincompose.core.extensions

import java.text.NumberFormat
import java.util.*

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

fun Double.formatToString(): String = NumberFormat.getInstance(Locale.getDefault())
    .format(this)
