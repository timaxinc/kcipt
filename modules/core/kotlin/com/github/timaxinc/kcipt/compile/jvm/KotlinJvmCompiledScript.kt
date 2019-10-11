package com.github.timaxinc.kcipt.compile.jvm

import com.github.timaxinc.kcipt.CompiledScript
import com.github.timaxinc.kcipt.Script
import com.github.timaxinc.kcipt.util.collections.delegate
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmCompiledModuleInMemory
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript
import kotlin.script.experimental.api.CompiledScript as KotlinCompiledScript

var CompiledScript.jvmRawClasses: Map<String, ByteArray> by delegate { mapOf<String, ByteArray>() }

class KotlinJvmCompiledScript(
        @Suppress("UNUSED")
        val kotlinJvmCompiledScript: KotlinCompiledScript<*>,
        script: Script
) : CompiledScript(script) {

    init {
        jvmRawClasses = kotlinJvmCompiledScript.rawClasses
    }

    companion object {
        internal val KotlinCompiledScript<*>.rawClasses: Map<String, ByteArray>
            get() = readRawClassesFromCompiledScript()

        private fun KotlinCompiledScript<*>.readRawClassesFromCompiledScript(): Map<String, ByteArray> {
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