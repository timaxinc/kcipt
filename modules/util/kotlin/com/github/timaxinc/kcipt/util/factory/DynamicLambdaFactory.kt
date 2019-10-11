package com.github.timaxinc.kcipt.util.factory

class DynamicLambdaFactory<P, T>(private val block: (P) -> T) : DynamicFactory<P, T> {
    override fun invoke(parameter: P) = block(parameter)
}