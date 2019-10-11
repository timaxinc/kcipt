package com.github.timaxinc.kcipt.compile.jvm

import com.github.timaxinc.kcipt.compile.CompilerReport
import kotlin.script.experimental.api.ScriptDiagnostic

/**
 * Bridge for [ScriptDiagnostic]
 *
 * @property diagnostic [ScriptDiagnostic] from kotlin scripting api
 */
class KotlinJvmScriptCompilerReport(val diagnostic: ScriptDiagnostic) : CompilerReport(diagnostic.message)
