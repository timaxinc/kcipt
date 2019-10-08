package com.github.timaxinc.kcipt.compile.jvm

import ScriptContext
import com.github.timaxinc.kcipt.CompiledScript
import com.github.timaxinc.kcipt.Script
import com.github.timaxinc.kcipt.compile.Compiler
import com.github.timaxinc.kcipt.compile.CompilerReport
import com.github.timaxinc.kcipt.result.Result
import com.github.timaxinc.kcipt.result.createSuccess
import com.github.timaxinc.kcipt.result.createFailure
import kotlin.script.experimental.api.*
import kotlin.script.experimental.api.CompiledScript as KotlinCompiledScript
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.JvmDependencyFromClassLoader
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

class KotlinJvmScriptCompiler(private val scriptingHost: BasicJvmScriptingHost = BasicJvmScriptingHost()) : Compiler {

    private val kotlinCompiler = scriptingHost.compiler

    override suspend fun invoke(script: Script): Result<CompiledScript, CompilerReport> {
        val compileResult: ResultWithDiagnostics<KotlinCompiledScript<*>> =
                kotlinCompiler.invoke(
                        script.text.toScriptSource(),
                        script.configuration.kotlinJvmScriptCompilationConfiguration
                )

        return if (compileResult is ResultWithDiagnostics.Failure) {
            createFailure(compileResult.reports.map { KotlinJvmScriptCompilerReport(it) })
        } else {
            createSuccess(KotlinJvmCompiledScript(compileResult.valueOrNull()!!, script))
        }
    }
}