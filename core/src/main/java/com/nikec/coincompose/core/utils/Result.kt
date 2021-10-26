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

package com.nikec.coincompose.core.utils

import androidx.annotation.Keep
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Result<T> {
    return try {
        Result.Success(apiCall())
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
