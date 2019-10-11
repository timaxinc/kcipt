package com.github.timaxinc.kcipt.source.provider

import com.github.timaxinc.kcipt.source.MutableSource
import com.github.timaxinc.kcipt.util.io.setupAsMutableFile
import java.io.File

class FileSource(private val file: File) : MutableSource<String> {

    constructor(path: String) : this(File(path))

    override fun write(t: String) {
        if (file.setupAsMutableFile()) file.writeText(t)
    }

    override fun read(): String {
        if (file.setupAsMutableFile()) return file.readText()
        return ""
    }
}
