package com.nikec.coincompose.core.data.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.nikec.coincompose.core.data.model.SparklineIn7d
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
