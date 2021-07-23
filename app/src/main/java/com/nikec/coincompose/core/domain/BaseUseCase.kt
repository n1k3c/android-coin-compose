package com.nikec.coincompose.core.domain

abstract class BaseUseCase<in P, R> {

    operator fun invoke(parameters: P): R = execute(parameters)

    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}