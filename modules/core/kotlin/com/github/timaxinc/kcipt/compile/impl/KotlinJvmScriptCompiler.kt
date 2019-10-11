package com.github.timaxinc.kcipt.compile.impl

import com.github.timaxinc.kcipt.CompiledScript
import com.github.timaxinc.kcipt.Script
import com.github.timaxinc.kcipt.compile.Compiler
import com.github.timaxinc.kcipt.compile.CompilerReport
import com.github.timaxinc.kcipt.result.Result
import com.github.timaxinc.kcipt.result.createFailure
import com.github.timaxinc.kcipt.result.createSuccess
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.resultOrNull
import kotlin.script.experimental.api.valueOrNull
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import kotlin.script.templates.standard.SimpleScriptTemplate
import kotlin.script.experimental.api.CompiledScript as KotlinCompiledScript

class KotlinJvmScriptCompiler(private val scriptingHost: BasicJvmScriptingHost = BasicJvmScriptingHost()) : Compiler {

    private val kotlinCompiler = scriptingHost.compiler

    override suspend fun invoke(script: Script): Result<CompiledScript, CompilerReport> {
        val compileResult: ResultWithDiagnostics<KotlinCompiledScript<*>> =
                kotlinCompiler.invoke(
                        script.text.toScriptSource(),
                        createJvmCompilationConfigurationFromTemplate<SimpleScriptTemplate> {})

        return if (compileResult is ResultWithDiagnostics.Failure) {
            createFailure(compileResult.reports.map { KotlinJvmScriptCompilerReport(it) })
        } else {
            createSuccess(KotlinJvmCompiledScript(compileResult.resultOrNull()!!, script))
        }
    }
}
