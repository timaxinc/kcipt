package com.github.timaxinc.kcipt.source.provider

import com.github.timaxinc.kcipt.source.Source
import com.github.timaxinc.kcipt.util.io.Resource

class ResourceContentSource(private val resourcePath: String) : Source<String> {
    override fun read(): String {
        return Resource(resourcePath).readText()
    }
}
