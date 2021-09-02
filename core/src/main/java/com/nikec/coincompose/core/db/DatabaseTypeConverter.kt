package com.nikec.coincompose.core.db

import androidx.room.TypeConverter
import com.nikec.coincompose.core.model.SparklineIn7d
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object DatabaseTypeConverter {

    @TypeConverter
    fun stringToSparkline(data: String): SparklineIn7d? {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return moshi.adapter(SparklineIn7d::class.java).lenient().fromJson(data)
    }

    @TypeConverter
    fun sparklineToString(sparkline: SparklineIn7d?): String {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return moshi.adapter(SparklineIn7d::class.java).lenient().toJson(sparkline)
    }
}
