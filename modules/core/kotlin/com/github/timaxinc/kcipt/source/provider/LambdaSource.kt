package com.github.timaxinc.kcipt.source.provider

import com.github.timaxinc.kcipt.source.Source

//todo add function to build in a dsl like way
class LambdaSource<T>(private val readBlock: () -> T) : Source<T> {

    override fun read() = readBlock()
}
