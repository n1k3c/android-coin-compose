/*
 * Developed by n1k3c (Nikola Curilović)  2021
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.nikec.coincompose.coins.common

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.nikec.coincompose.coins.R
import com.nikec.coincompose.core.ui.theme.Green
import com.nikec.coincompose.core.ui.theme.Red

enum class PercentageChangeHeader(@StringRes val value: Int) {
    ONE_HOUR(R.string.one_hour),
    TWENTY_FOUR_HOURS(R.string.twenty_four_hours),
    SEVEN_DAYS(R.string.seven_days),
    THIRTY_DAYS(R.string.thirty_days),
    ONE_YEAR(R.string.one_year)
}

fun percentageChangeColorText(price: Double): Color = if (price < 0) Red else Green
