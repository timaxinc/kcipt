package com.github.timaxinc.kcipt.compile.impl

import com.github.timaxinc.kcipt.CompiledScript
import com.github.timaxinc.kcipt.Script
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmCompiledModuleInMemory
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript
import kotlin.script.experimental.api.CompiledScript as KotlinCompiledScript

class KotlinJvmCompiledScript(
        val compiledScript: KotlinCompiledScript<*>,
        script: Script
) : CompiledScript(
        script,
        compiledScript.rawClasses
) {
    companion object {
        internal val KotlinCompiledScript<*>.rawClasses: Map<String, ByteArray>
            get() = readRawClassesFromCompiledScript()

        internal fun KotlinCompiledScript<*>.readRawClassesFromCompiledScript(): Map<String, ByteArray> {
            val jvmCompiledScript = this as KJvmCompiledScript
            val jvmCompiledModule = jvmCompiledScript.compiledModule as KJvmCompiledModuleInMemory

            val compilerOutputFiles = jvmCompiledModule.compilerOutputFiles

            return compilerOutputFiles.filterKeys { it.endsWith(".class") }.map {
                it.key.removeSuffix(".class")
                        .replace("/", ".")
                        .replace("\\", ".") to it.value
            }.toMap()
        }
    }
}
