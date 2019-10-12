package com.github.timaxinc.kcipt.util.io

import java.io.File
import java.io.IOException

/**
 * tris to create the [File] if it doesn't exists
 *
 * @return if [File] exists after creation
 */
fun File.createFileIfNotExists(): Boolean {
    try {
        if (!exists()) {
            parentFile?.mkdirs()
            createNewFile()
        }
    } catch (e: IOException) {
    }
    return exists()
}

/**
 * tris to create the [File] as Folder if it doesn't exists
 *
 * @return if folder exists after creation
 */
fun File.createFolderIfNotExists(): Boolean {
    try {
        if (!exists()) {
            parentFile?.mkdirs()
            mkdir()
        }
    } catch (e: IOException) {
    }
    return exists()
}

/**
 * tris to setup a file as read and writeable
 *
 * @return if setup was successful
 */
fun File.setupAsMutableFile(): Boolean {
    if (isFile && canRead() && canWrite()) {
        return createFileIfNotExists()
    }
    return false
}

/**
 * splits [File] path
 *
 * @return [SplintedFilePath] that contains the splinted version of [File] path
 */
fun File.splintFilePath(): SplintedFilePath {
    var fullPath = absolutePath
    fullPath = fullPath.replace("\\", "/")
    val directory = fullPath.substringBeforeLast("/")
    val fullName = fullPath.substringAfterLast("/")
    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")
    return SplintedFilePath(directory, fullName, fileName, extension)
}

/**
 * the [SplintedFilePath] that contains the splinted version of [File] path
 */
val File.splintedFilePath: SplintedFilePath
    get() = splintFilePath()

/**
 * represents a file path in a splinted form
 *
 * @property directory the directory the [File] is located in
 * @property fullName the full name of the [File]
 * @property fileName the name of [File]
 * @property extension the [File] extension
 */
data class SplintedFilePath(var directory: String, var fullName: String, var fileName: String, var extension: String)
