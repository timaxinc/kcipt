package com.github.timaxinc.kcipt.util.factory

fun <T> factory(block: () -> T) = LambdaFactory<T>(block)
fun <P, T> dynamicFactory(block: (P) -> T) = DynamicLambdaFactory<P, T>(block)