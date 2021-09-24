package com.nikec.coincompose.core.utils

import androidx.annotation.Keep
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

suspend fun <T> safeApiCall(
    coroutineContext: CoroutineContext,
    apiCall: suspend () -> T
): Result<T> {
    return try {
        withContext(coroutineContext) {
            Result.Success(apiCall())
        }
    } catch (e: Throwable) {
        when (e) {
            is IOException -> Result.NetworkError(NoInternetThrowable)
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

    data class NetworkError(val noInternetThrowable: NoInternetThrowable) : Result<Nothing>()
    data class UnknownError(val throwable: Throwable) : Result<Nothing>()
}

object NoInternetThrowable : Throwable("No internet connection.")
