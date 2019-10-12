package com.github.timaxinc.kcipt.execute

import com.github.timaxinc.kcipt.CompiledScript

/**
 * should be implemented by every kcipt script executor implementation
 */
interface Executor {

    /**
     * executes a given [CompiledScript]
     *
     * @param compiledScript the [CompiledScript] that will be executed
     */
    operator fun invoke(compiledScript: CompiledScript)
}
