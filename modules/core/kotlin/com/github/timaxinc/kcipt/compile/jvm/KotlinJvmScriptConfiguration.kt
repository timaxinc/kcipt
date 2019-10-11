package com.github.timaxinc.kcipt.compile.jvm

import com.github.timaxinc.kcipt.ScriptConfiguration
import com.github.timaxinc.kcipt.util.collections.delegate
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.ScriptDependency

var ScriptConfiguration.kotlinJvmScriptCompilationConfiguration: ScriptCompilationConfiguration by delegate {
    ScriptCompilationConfiguration {}
}


var ScriptConfiguration.kotlinJvmScriptDependencies: MutableList<ScriptDependency> by delegate {
    mutableListOf<ScriptDependency>()
}