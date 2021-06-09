package com.nikec.coincompose.core.utils

import androidx.annotation.Keep
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.Success(apiCall())
    } catch (e: Throwable) {
        when (e) {
            is IOException -> Result.NetworkError
            is HttpException -> {
                try {
                    Result.HttpError(e)
                } catch (e: Exception) {
                    Result.UnknownError(e)
                }
            }
            else -> Result.UnknownError(e)
        }
    }
}

@Keep
sealed class Result<out T> {
    data class Success<out T>(val payload: T) : Result<T>()
    data class HttpError(val exception: HttpException) :
        Result<Nothing>()

    object NetworkError : Result<Nothing>()
    data class UnknownError(val throwable: Throwable) : Result<Nothing>()
}
