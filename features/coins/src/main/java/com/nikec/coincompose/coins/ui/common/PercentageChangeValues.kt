package com.nikec.coincompose.coins.ui.common

import androidx.annotation.StringRes
import com.nikec.coincompose.coins.R

enum class PercentageChangeValues(@StringRes val value: Int) {
    ONE_HOUR(R.string.one_hour),
    TWENTY_FOUR_HOURS(R.string.twenty_four_hours),
    SEVEN_DAYS(R.string.seven_days),
    THIRTY_DAYS(R.string.thirty_days),
    ONE_YEAR(R.string.one_year)
}
