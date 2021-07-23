package com.nikec.coincompose.core.utils

abstract class BaseUseCase<in P, R> {

    operator fun invoke(parameters: P): R = execute(parameters)

    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}