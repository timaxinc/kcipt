package com.github.timaxinc.kcipt.util.factory

/**
 * creates a object with type [T]
 *
 * @param T type of the created object
 */
interface Factory<T> {

    /**
     * creates a object with type [T]
     *
     * @return created object with type [T]
     */
    operator fun invoke(): T
}
