package com.github.timaxinc.kcipt.source

interface Source<T> {

    fun read(): T
}
