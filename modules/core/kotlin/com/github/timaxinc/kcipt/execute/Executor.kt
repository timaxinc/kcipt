package com.github.timaxinc.kcipt.execute

import com.github.timaxinc.kcipt.CompiledScript

/**
 * should be implemented by every kcipt script executor implementation
 */
interface Executor {

    operator fun invoke(compiledScript: CompiledScript)
}
