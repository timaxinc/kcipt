package com.github.timaxinc.kcipt.source.provider

import com.github.timaxinc.kcipt.source.MutableSource

/**
 * creates a [MutableLambdaSource]
 *
 * @param readBlock
 * @param writeBlock
 */
fun <T> mutableSource(readBlock: () -> T, writeBlock: T.() -> Unit) = MutableLambdaSource(readBlock, writeBlock)

/**
 * [MutableLambdaSource] implementation that uses a lambdas to implement [read] and [write]
 *
 * @property readBlock
 * @property writeBlock
 */
class MutableLambdaSource<T>(private val readBlock: () -> T, private val writeBlock: T.() -> Unit) : MutableSource<T> {

    /**
     * calls [writeBlock]
     *
     * @return result of [writeBlock]
     */
    override fun write(t: T) = t.writeBlock()

    /**
     * calls [readBlock]
     *
     * @return result of [readBlock]
     */
    override fun read() = readBlock()
}
