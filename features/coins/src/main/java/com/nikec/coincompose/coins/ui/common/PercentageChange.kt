package com.nikec.coincompose.coins.ui.common

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.nikec.coincompose.coins.R
import com.nikec.core.ui.theme.Green
import com.nikec.core.ui.theme.Red

enum class PercentageChangeHeader(@StringRes val value: Int) {
    ONE_HOUR(R.string.one_hour),
    TWENTY_FOUR_HOURS(R.string.twenty_four_hours),
    SEVEN_DAYS(R.string.seven_days),
    THIRTY_DAYS(R.string.thirty_days),
    ONE_YEAR(R.string.one_year)
}

fun percentageChangeColorText(price: Double): Color = if (price < 0) Red else Green
