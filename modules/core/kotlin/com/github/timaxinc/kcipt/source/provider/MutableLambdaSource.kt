package com.github.timaxinc.kcipt.source.provider

import com.github.timaxinc.kcipt.source.MutableSource

//todo add function to build in a dsl like way
class MutableLambdaSource<T>(private val readBlock: () -> T, private val writeBlock: T.() -> Unit) : MutableSource<T> {


    override fun write(t: T) = t.writeBlock()

    override fun read() = readBlock()
}
