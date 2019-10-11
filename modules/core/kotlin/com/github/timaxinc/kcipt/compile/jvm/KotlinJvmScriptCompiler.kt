package com.github.timaxinc.kcipt.compile.jvm

import com.github.timaxinc.kcipt.CompiledScript
import com.github.timaxinc.kcipt.Script
import com.github.timaxinc.kcipt.compile.Compiler
import com.github.timaxinc.kcipt.compile.CompilerReport
import com.github.timaxinc.kcipt.result.Result
import com.github.timaxinc.kcipt.result.createFailure
import com.github.timaxinc.kcipt.result.createSuccess
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.ScriptCompiler
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.valueOrNull
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.defaultJvmScriptingHostConfiguration
import kotlin.script.experimental.jvmhost.JvmScriptCompiler
import kotlin.script.experimental.api.CompiledScript as KotlinCompiledScript

/**
 * [KotlinJvmScriptCompiler] is a [Compiler] implementation based on [JvmScriptCompiler]
 *
 * @property kotlinCompiler the Jetbrains [JvmScriptCompiler]
 */
class KotlinJvmScriptCompiler(
        private val kotlinCompiler: ScriptCompiler = JvmScriptCompiler(defaultJvmScriptingHostConfiguration)
) : Compiler {

    /**
     * Compiles a given [script]
     *
     * @param script the [Script] that will be compiled
     * @return a [Result] in case a compiling succeeds a [Result.Success] containing a [CompiledScript] else a
     * [Result.Failure] with reports of type [KotlinJvmScriptCompilerReport] containing [ScriptDiagnostic]
     */
    override suspend fun invoke(script: Script): Result<CompiledScript, CompilerReport> {
        val compileResult: ResultWithDiagnostics<KotlinCompiledScript<*>> = kotlinCompiler.invoke(
                script.text.toScriptSource(), script.configuration.kotlinJvmScriptCompilationConfiguration
        )

        return if (compileResult is ResultWithDiagnostics.Failure) {
            createFailure(compileResult.reports.map { KotlinJvmScriptCompilerReport(it) })
        } else {
            createSuccess(KotlinJvmCompiledScript(compileResult.valueOrNull()!!, script))
        }
    }
}
