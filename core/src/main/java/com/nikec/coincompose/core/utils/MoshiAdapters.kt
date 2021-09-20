package com.nikec.coincompose.core.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

enum class DatePattern(val value: String) {
    COINS("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
    NEWS("yyyy-MM-dd'T'HH:mm:ss'Z'")
}

class DateAdapter(private val datePattern: DatePattern) {

    @FromJson
    fun dateFromJson(dateString: String): LocalDateTime? {
        val dateFormat =
            DateTimeFormatter.ofPattern(datePattern.value)
        return LocalDateTime.parse(dateString, dateFormat).atOffset(ZoneOffset.UTC)
            .atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
    }

    @ToJson
    fun dateToJson(value: LocalDateTime?): String {
        val dateFormat =
            DateTimeFormatter.ofPattern(datePattern.value)
        value?.atOffset(ZoneOffset.UTC)?.atZoneSameInstant(ZoneId.systemDefault())
        return dateFormat.format(value)
    }
}
