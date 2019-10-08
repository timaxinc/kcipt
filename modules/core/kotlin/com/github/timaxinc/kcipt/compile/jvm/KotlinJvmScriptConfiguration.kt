package com.github.timaxinc.kcipt.compile.jvm

import com.github.timaxinc.kcipt.ScriptConfiguration
import com.github.timaxinc.kcipt.delegate
import kotlin.script.experimental.api.ScriptCompilationConfiguration

var ScriptConfiguration.kotlinJvmScriptCompilationConfiguration: ScriptCompilationConfiguration by delegate(ScriptCompilationConfiguration {})