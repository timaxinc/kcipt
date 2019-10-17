package com.github.timaxinc.kcipt.util.factory

/**
 * creates a object with type [T] based on a given parameter with type [P]
 *
 * @param P type of the parameter
 * @param T type of the created object
 */
interface DynamicFactory<P, T> {

    /**
     * creates a object with type [T] based on a given [parameter] with type [P]
     *
     * @param parameter
     * @return created object with type [T]
     */
    operator fun invoke(parameter: P): T
}
