package util

import org.gradle.api.initialization.Settings
import java.io.File

fun Settings.includeFrom(dir: String, name: String) {
    include(name)
    val moculeDir =
            "./$dir/${(if (name.startsWith(":")) name.replaceFirst(":", "") else name).replace(":", "/")}"
    project(name).projectDir = File(moculeDir)
    val settingsScriptPath = "./$dir/$name/settings.gradle.kts"
    val settingsScriptFile = File(settingsScriptPath)
    if (settingsScriptFile.exists() && settingsScriptFile.isFile) {
        apply {
            from(settingsScriptFile)
        }
    }
}

fun Settings.includeFromModuleDir(name: String) = includeFrom("modules", name)

@Suppress("UNUSED")
fun Settings.includeModule(name: String) = includeFrom(".", name)
