package com.github.timaxinc.kcipt.compile.jvm

import com.github.timaxinc.kcipt.CompiledScript
import com.github.timaxinc.kcipt.Script
import com.github.timaxinc.kcipt.util.collections.delegate
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmCompiledModuleInMemory
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript
import kotlin.script.experimental.api.CompiledScript as KotlinCompiledScript

/**
 * Used to store classes in raw bytecode in a map. Key is the full class name. Value is the bytecode.
 */
var CompiledScript.jvmRawClasses: Map<String, ByteArray> by delegate { mapOf<String, ByteArray>() }

/**
 * [KotlinJvmCompiledScript] describes a kotlin script that was compiled on and for jvm.
 * Is a used as a bridge to [KotlinCompiledScript].
 *
 * @property kotlinJvmCompiledScript the CompiledScript from the Jetbrains kotlin script compiler
 *
 * @constructor
 * @param script the source script that was compiled
 */
class KotlinJvmCompiledScript(val kotlinJvmCompiledScript: KotlinCompiledScript<*>, script: Script) : CompiledScript(
        script
) {

    init {
        jvmRawClasses = kotlinJvmCompiledScript.readRawClassesFromCompiledScript()
    }

    /**
     * reads raw classes from a [KotlinCompiledScript]
     *
     * @return a map of full class name and bytecode
     */
    private fun KotlinCompiledScript<*>.readRawClassesFromCompiledScript(): Map<String, ByteArray> {
        val jvmCompiledScript = this as KJvmCompiledScript
        val jvmCompiledModule = jvmCompiledScript.compiledModule as KJvmCompiledModuleInMemory

        val compilerOutputFiles = jvmCompiledModule.compilerOutputFiles

        return compilerOutputFiles.filterKeys { it.endsWith(".class") }.map {
            it.key.removeSuffix(".class").replace("/", ".").replace("\\", ".") to it.value
        }.toMap()
    }
}
