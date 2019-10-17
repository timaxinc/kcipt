package com.github.timaxinc.kcipt.compile.jvm

import com.github.timaxinc.kcipt.ScriptConfiguration
import com.github.timaxinc.kcipt.util.collections.delegate
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.ScriptDependency

/**
 * configures the [ScriptCompilationConfiguration] used in [KotlinJvmScriptCompiler] to configure kotlin scripting api
 * compiler
 */
var ScriptConfiguration.kotlinJvmScriptCompilationConfiguration: ScriptCompilationConfiguration by delegate {
    ScriptCompilationConfiguration {}
}

/**
 * configures a list of [ScriptDependency] used in [KotlinJvmScriptCompiler] to configure kotlin scripting api script
 * dependencies
 */
var ScriptConfiguration.kotlinJvmScriptDependencies: MutableList<ScriptDependency> by delegate {
    mutableListOf<ScriptDependency>()
}
