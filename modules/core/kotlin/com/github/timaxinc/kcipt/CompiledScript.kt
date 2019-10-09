package com.github.timaxinc.kcipt

open class CompiledScript(source: Script) : Script by source, MutableMap<String, Any> by mutableMapOf()