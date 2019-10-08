package com.github.timaxinc.kcipt

open class ScriptConfiguration(private val impl: MutableMap<String, Any> = mutableMapOf()) : MutableMap<String, Any> by impl