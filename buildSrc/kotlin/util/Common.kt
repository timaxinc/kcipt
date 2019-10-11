package util

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSetOutput
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.the
import java.io.File

inline fun <reified T : Task> Project.task(noinline configuration: T.() -> Unit) =
        tasks.creating(T::class, configuration)

inline fun <T : Any> Project.withJavaPlugin(crossinline body: () -> T?): T? {
    var res: T? = null
    pluginManager.withPlugin("java") {
        res = body()
    }
    return res
}

fun Project.getCompiledClasses(): SourceSetOutput? = withJavaPlugin { mainSourceSet.output }

fun Project.getSources(): SourceDirectorySet? = withJavaPlugin { mainSourceSet.allSource }

fun Project.getResourceFiles(): SourceDirectorySet? = withJavaPlugin { mainSourceSet.resources }

fun fileFrom(root: File, vararg children: String): File = children.fold(root) { f, c -> File(f, c) }

fun fileFrom(root: String, vararg children: String): File = children.fold(File(root)) { f, c -> File(f, c) }

var Project.jvmTarget: String?
    get() = extra.takeIf { it.has("util.getJvmTarget") }?.get("util.getJvmTarget") as? String
    set(v) {
        extra["util.getJvmTarget"] = v
    }

var Project.javaHome: String?
    get() = extra.takeIf { it.has("util.getJavaHome") }?.get("util.getJavaHome") as? String
    set(v) {
        extra["util.getJavaHome"] = v
    }

fun Project.javaPluginConvention(): JavaPluginConvention = the()

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

val ALPHANUMERIC_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"

fun randomString(length: Int, chars: CharSequence = ALPHANUMERIC_CHARS): String {
    val randStr = StringBuilder()
    for (i in 0 until length) {
        randStr.append(chars.random())
    }
    return randStr.toString()
}

fun randomAlphanumericString(length: Int) = randomString(length, ALPHANUMERIC_CHARS)

val DEFAULT_DATE_TIME_FORMAT by lazy { DateFormat("dd.MM.yyyy-HH:mm:ss:SSS") }

fun DateTime.toTimeStamp(): String {
    return "[${format(DEFAULT_DATE_TIME_FORMAT)}]"
}

fun DateTime.formatDefault() = format(DEFAULT_DATE_TIME_FORMAT)
