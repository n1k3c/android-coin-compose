package com.nikec.coincompose.core.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun LocalDateTime.formatLocalized(): String =
    this.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))