package com.github.timaxinc.kcipt.execute

import com.github.timaxinc.kcipt.CompiledScript

interface Executor {
    operator fun invoke(compiledScript: CompiledScript)
}