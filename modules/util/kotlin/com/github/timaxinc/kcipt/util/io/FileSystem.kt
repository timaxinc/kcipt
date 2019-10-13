package com.github.timaxinc.kcipt.util.io

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

/**
 * tris to create the path as file if it doesn't exists
 *
 * @return if file exists after creation
 */
fun Path.createFileIfNotExists(): Boolean {
    try {
        if (!Files.exists(this)) {
            Files.createDirectories(this)
            Files.createFile(this)
        }
    } catch (e: IOException) {
    }
    return Files.exists(this)
}

/**
 * tris to create the path as Folder if it doesn't exists
 *
 * @return if folder exists after creation
 */
fun Path.createFolderIfNotExists(): Boolean {
    try {
        if (!Files.exists(this)) {
            Files.createDirectories(this)
            Files.createDirectory(this)
        }
    } catch (e: IOException) {
    }
    return Files.exists(this)
}

/**
 * tris to setup a file as read and writeable
 *
 * @return if setup was successful
 */
fun Path.setupAsMutableFile(): Boolean {
    if (Files.isRegularFile(this) && Files.isReadable(this) && Files.isWritable(this)) {
        return createFileIfNotExists()
    }
    return false
}

/**
 * splits [Path]
 *
 * @return [SplintedPath] that contains the splinted version of [Path]
 */
fun Path.splintFilePath(): SplintedPath {
    var fullPath = toFile().toString()
    fullPath = fullPath.replace("\\", "/")
    val directory = fullPath.substringBeforeLast("/")
    val fullName = fullPath.substringAfterLast("/")
    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")
    return SplintedPath(directory, fullName, fileName, extension)
}

/**
 * the [SplintedPath] that contains the splinted version of [Path] path
 */
val Path.splintedFilePath: SplintedPath
    get() = splintFilePath()

/**
 * represents a path in a splinted form
 *
 * @property directory the directory the file
 * @property fullName the full name of the file
 * @property fileName the name of file
 * @property extension the file extension
 */
data class SplintedPath(var directory: String, var fullName: String, var fileName: String, var extension: String)
