package com.nikec.coincompose.core.utils

import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

abstract class SuspendUseCase<in P, R>(private val coroutineDispatcher: CoroutineContext) {

    suspend operator fun invoke(parameters: P): Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    Result.Success(it)
                }
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.NetworkError(NoInternetThrowable)
                is HttpException -> {
                    Result.HttpError(e)
                }
                else -> Result.UnknownError(e)
            }
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}
