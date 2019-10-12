package com.github.timaxinc.kcipt.source

/**
 * abstraction of a lazily readable source
 * used to wait with a heavy construction of a object until it is used
 *
 * @param T
 */
interface Source<T> {

    /**
     * used to lazily construct a object of type [T] wen is needed
     *
     * @return a object of type [T]
     */
    fun read(): T
}
