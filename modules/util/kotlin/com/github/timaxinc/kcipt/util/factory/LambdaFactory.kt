package com.github.timaxinc.kcipt.util.factory

/**
 * creates a [LambdaFactory] with [block]
 *
 * @param block the lambda used to create the object
 */
fun <T> factory(block: () -> T) = LambdaFactory<T>(block)

/**
 * implementation of [Factory] that uses a lambda [block] to create a object
 *
 * @property block the lambda used to create the object
 */
class LambdaFactory<T>(private val block: () -> T) : Factory<T> {

    /**
     * creates a object with type [T] by calling [block]
     *
     * @return created object with type [T]
     */
    override fun invoke() = block()
}
