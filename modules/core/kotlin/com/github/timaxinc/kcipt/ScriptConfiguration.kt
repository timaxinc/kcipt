package com.github.timaxinc.kcipt

/**
 * configures [Script] compilation and execution
 *
 * @property impl the map that stores configuration
 */
open class ScriptConfiguration(private val impl: MutableMap<String, Any> = mutableMapOf()) : MutableMap<String, Any> by impl
