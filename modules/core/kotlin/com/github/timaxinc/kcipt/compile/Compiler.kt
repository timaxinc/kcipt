package com.github.timaxinc.kcipt.compile

import com.github.timaxinc.kcipt.CompiledScript
import com.github.timaxinc.kcipt.Script
import com.github.timaxinc.kcipt.result.Result

interface Compiler {
    suspend operator fun invoke(script: Script): Result<CompiledScript, CompilerReport>
}