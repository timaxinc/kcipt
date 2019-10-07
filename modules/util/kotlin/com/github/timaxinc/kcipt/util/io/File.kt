package com.github.timaxinc.kcipt.util.io

import java.io.File

fun File.createFileIfNotExists(): Boolean {
    if (!exists()) {
        parentFile?.mkdirs()
        createNewFile()
    }
    return exists()
}

fun File.createFolderIfNotExists(): Boolean {
    if (!exists()) {
        parentFile?.mkdirs()
        mkdir()
    }
    return exists()
}

fun File.setupAsMutableFile(): Boolean {
    if (isFile && canRead() && canWrite()) {
        return createFileIfNotExists()
    }
    return false
}

fun File.splintedFilePath(): SplintedFilePath {
    var fullPath = absolutePath
    fullPath = fullPath.replace("\\", "/")
    val directory = fullPath.substringBeforeLast("/")
    val fullName = fullPath.substringAfterLast("/")
    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")
    return SplintedFilePath(directory, fullName, fileName, extension)
}

val File.splintedFilePath: SplintedFilePath
    get() = splintedFilePath()

data class SplintedFilePath(var directory: String, var fullName: String, var fileName: String, var extension: String)
