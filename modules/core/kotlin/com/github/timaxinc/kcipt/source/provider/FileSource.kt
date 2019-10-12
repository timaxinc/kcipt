package com.github.timaxinc.kcipt.source.provider

import com.github.timaxinc.kcipt.source.MutableSource
import com.github.timaxinc.kcipt.util.io.setupAsMutableFile
import java.io.File

/**
 * a [MutableSource] that wraps a given [File]
 *
 * @property file the wrapped [File]
 */
class FileSource(private val file: File) : MutableSource<String> {

    constructor(path: String) : this(File(path))

    /**
     * tris to read content of [File] [file]
     *
     * @return the content of [File] [file] or a empty [String]
     */
    override fun read(): String {
        if (file.setupAsMutableFile()) return file.readText()
        return ""
    }

    /**
     * tris to write [t] in [File] [file]
     *
     * @param t the [String] to write
     */
    override fun write(t: String) {
        if (file.setupAsMutableFile()) file.writeText(t)
    }
}
