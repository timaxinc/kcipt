package com.github.timaxinc.kcipt.util.factory

interface Factory<T> {
    operator fun invoke(): T
}