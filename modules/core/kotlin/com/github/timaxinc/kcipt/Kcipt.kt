package com.github.timaxinc.kcipt

import com.github.timaxinc.kcipt.compile.Compiler
import com.github.timaxinc.kcipt.execute.Executor

class Kcipt(val compiler: Compiler, val executor: Executor) {

    fun compile(script: Script): CompiledScript = TODO("implement compiler delegate")

    fun execute(compiledScript: CompiledScript): CompiledScript = TODO("implement executor delegate")
}
