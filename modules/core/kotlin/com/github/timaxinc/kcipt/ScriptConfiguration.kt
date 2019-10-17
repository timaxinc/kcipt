package com.github.timaxinc.kcipt

import DefaultScriptContext
import ScriptContext
import com.github.timaxinc.kcipt.util.collections.delegate
import kotlin.reflect.KClass

/**
 * configures the context class of a [Script]
 */
var ScriptConfiguration.contextClass: KClass<out ScriptContext> by delegate { DefaultScriptContext::class }

/**
 * configures [Script] compilation and execution
 *
 * @property impl the map that stores configuration
 */
open class ScriptConfiguration(
        private val impl: MutableMap<String, Any> = mutableMapOf()
) : MutableMap<String, Any> by impl {

    constructor(impl: MutableMap<String, Any> = mutableMapOf(), block: ScriptConfiguration.() -> Unit) : this(impl) {
        apply(block)
    }
}
