package com.nikec.coincompose.core.utils

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()