package com.github.timaxinc.kcipt.source

/**
 * [Source] with ability to read and write
 *
 * @param T
 */
interface MutableSource<T> : Source<T> {

    /**
     * writes [t]
     * after call [read] should return a object equal to the written one
     *
     * @param t object to write
     */
    fun write(t: T)
}
