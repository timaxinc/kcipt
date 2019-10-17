package com.github.timaxinc.kcipt.source.provider

import com.github.timaxinc.kcipt.source.MutableSource
import com.github.timaxinc.kcipt.util.io.setupAsMutableFile
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

/**
 * a [MutableSource] that wraps a given [Path]
 *
 * @property path the wrapped [Path]
 */
class FileSource(private val path: Path) : MutableSource<String> {

    constructor(path: String) : this(FileSystems.getDefault().getPath(path))

    /**
     * tris to read content of [path]
     *
     * @return the content of [path] or a empty [String]
     */
    override fun read(): String {
        if (path.setupAsMutableFile()) return String(Files.readAllBytes(path))
        return ""
    }

    /**
     * tris to write [t] in file corresponding to [path]
     *
     * @param t the [String] to write
     */
    override fun write(t: String) {
        if (path.setupAsMutableFile()) Files.write(path, t.toByteArray())
    }
}
