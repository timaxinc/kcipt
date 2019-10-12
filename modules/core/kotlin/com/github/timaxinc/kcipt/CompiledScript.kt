package com.github.timaxinc.kcipt

/**
 * representation of a successfully compiled [Script]
 *
 * @constructor
 * @param source the successfully compiled [Script]
 */
open class CompiledScript(source: Script) : Script by source, MutableMap<String, Any> by mutableMapOf()
