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
