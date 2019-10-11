package com.github.timaxinc.kcipt.compile

import com.github.timaxinc.kcipt.CompiledScript
import com.github.timaxinc.kcipt.Script
import com.github.timaxinc.kcipt.result.Result

/**
 * should be implemented by every kcipt script compiler implementation
 */
interface Compiler {

    /**
     * Compiles a given [script]
     *
     * @param script the [Script] that will be compiled
     * @return a [Result] in case a compiling succeeds a [Result.Success] containing a [CompiledScript] else a
     * [Result.Failure] with reports of type [CompilerReport]
     */
    suspend operator fun invoke(script: Script): Result<CompiledScript, CompilerReport>
}