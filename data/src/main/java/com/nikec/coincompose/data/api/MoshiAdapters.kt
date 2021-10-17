package com.nikec.coincompose.data.api

import com.github.ajalt.timberkt.e
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

        return try {
            LocalDateTime.parse(dateString, dateFormat).atOffset(ZoneOffset.UTC)
                .atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
        } catch (e: Exception) {
            e { "Can not parse date: $e" }
            null
        }
    }

    @ToJson
    fun dateToJson(value: LocalDateTime?): String {
        val dateFormat =
            DateTimeFormatter.ofPattern(datePattern.value)
        value?.atOffset(ZoneOffset.UTC)?.atZoneSameInstant(ZoneId.systemDefault())
        return dateFormat.format(value)
    }
}
