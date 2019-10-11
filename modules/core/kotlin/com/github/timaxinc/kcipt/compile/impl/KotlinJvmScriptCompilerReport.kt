package com.github.timaxinc.kcipt.compile.impl

import com.github.timaxinc.kcipt.compile.CompilerReport
import kotlin.script.experimental.api.ScriptDiagnostic

class KotlinJvmScriptCompilerReport(val diagnostic: ScriptDiagnostic) : CompilerReport(diagnostic.message)
