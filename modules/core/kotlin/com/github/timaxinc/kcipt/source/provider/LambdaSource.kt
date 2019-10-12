package com.github.timaxinc.kcipt.source.provider

import com.github.timaxinc.kcipt.source.Source

/**
 * creates a [LambdaSource] with a given [readBlock]
 *
 * @param readBlock
 */
fun <T> source(readBlock: () -> T) = LambdaSource<T>(readBlock)

/**
 * [Source] implementation that uses a lambda to implement [read]
 *
 * @property readBlock
 */
class LambdaSource<T>(private val readBlock: () -> T) : Source<T> {

    /**
     * calls [readBlock]
     *
     * @return result of [readBlock]
     */
    override fun read() = readBlock()
}
