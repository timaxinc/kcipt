package com.github.timaxinc.kcipt.classloader

import java.net.URL
import java.util.*

class URLEnumeration(private val enums: List<Enumeration<URL>>) : Enumeration<URL> {
    private var index: Int = 0

    override fun hasMoreElements(): Boolean = this.next()

    override fun nextElement(): URL {
        return if (!this.next()) {
            throw NoSuchElementException()
        } else {
            this.enums[this.index].nextElement()
        }
    }

    private operator fun next(): Boolean {
        while (this.index < this.enums.size) {
            if (this.enums[this.index].hasMoreElements()) {
                return true
            }

            ++this.index
        }

        return false
    }

}