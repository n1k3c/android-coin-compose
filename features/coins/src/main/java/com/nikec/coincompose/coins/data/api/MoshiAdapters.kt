package com.nikec.coincompose.coins.data.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

class DateAdapter {

    @FromJson
    fun dateFromJson(dateString: String): LocalDateTime? {
        val dateFormat =
            DateTimeFormatter.ofPattern(DATE_PATTERN)
        return LocalDateTime.parse(dateString, dateFormat).atOffset(ZoneOffset.UTC)
            .atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
    }

    @ToJson
    fun dateToJson(value: LocalDateTime?): String {
        val dateFormat =
            DateTimeFormatter.ofPattern(DATE_PATTERN)
        value?.atOffset(ZoneOffset.UTC)?.atZoneSameInstant(ZoneId.systemDefault())
        return dateFormat.format(value)
    }
}
