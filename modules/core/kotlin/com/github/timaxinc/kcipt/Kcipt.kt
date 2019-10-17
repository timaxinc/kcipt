package com.github.timaxinc.kcipt

import com.github.timaxinc.kcipt.compile.Compiler
import com.github.timaxinc.kcipt.execute.Executor

/**
 * used as to combine a [Compiler] and a corresponding [Executor]
 *
 * @property compiler
 * @property executor
 */
class Kcipt(val compiler: Compiler, val executor: Executor) {

    //TODO doc
    fun compile(script: Script): CompiledScript = TODO("implement compiler delegate")

    //TODO doc
    fun execute(compiledScript: CompiledScript): CompiledScript = TODO("implement executor delegate")
}
