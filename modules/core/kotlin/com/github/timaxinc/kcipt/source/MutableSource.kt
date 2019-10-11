package com.github.timaxinc.kcipt.source

interface MutableSource<T> : Source<T> {

    fun write(t: T)
}
