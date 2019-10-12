package com.github.timaxinc.kcipt.source.provider

import com.github.timaxinc.kcipt.source.Source
import com.github.timaxinc.kcipt.util.io.Resource

/**
 * a [Source] that wraps a [Resource] with a given path
 *
 * @property resourcePath
 */
class ResourceContentSource(private val resourcePath: String) : Source<String> {

    /**
     * tris to read content of [Resource] with path [resourcePath]
     *
     * @return the content of the [Resource]
     */
    override fun read(): String {
        return Resource(resourcePath).readText()
    }
}
