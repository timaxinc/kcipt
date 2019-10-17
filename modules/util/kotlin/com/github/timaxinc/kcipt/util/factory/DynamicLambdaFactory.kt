package com.github.timaxinc.kcipt.util.factory

/**
 * creates a [DynamicLambdaFactory] with [block]
 *
 * @param block the lambda used to create the object
 */
fun <P, T> dynamicFactory(block: (P) -> T) = DynamicLambdaFactory<P, T>(block)

/**
 * implementation of [DynamicFactory] that uses a lambda [block] to create a object
 *
 * @property block the lambda used to create the object
 */
class DynamicLambdaFactory<P, T>(private val block: (P) -> T) : DynamicFactory<P, T> {

    /**
     * creates a object with type [T] based on a given [parameter] with type [P] by calling [block]
     *
     * @param parameter
     * @return created object with type [T]
     */
    override fun invoke(parameter: P) = block(parameter)
}
