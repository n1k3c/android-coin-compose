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

package com.nikec.coincompose.data.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.nikec.coincompose.data.model.SparklineIn7d
import com.squareup.moshi.Moshi
import java.time.LocalDateTime
import java.time.ZoneOffset

@ProvidedTypeConverter
class DatabaseTypeConverter(private val moshi: Moshi) {

    @TypeConverter
    fun stringToSparkline(data: String): SparklineIn7d? {
        return moshi.adapter(SparklineIn7d::class.java).lenient().fromJson(data)
    }

    @TypeConverter
    fun sparklineToString(sparkline: SparklineIn7d?): String {
        return moshi.adapter(SparklineIn7d::class.java).lenient().toJson(sparkline)
    }

    @TypeConverter
    fun toLocalDateTime(timestamp: Long?): LocalDateTime? {
        return timestamp?.let {
            LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
        }
    }

    @TypeConverter
    fun fromLocalDateTime(time: LocalDateTime?): Long? {
        return time?.toEpochSecond(ZoneOffset.UTC)
    }
}
