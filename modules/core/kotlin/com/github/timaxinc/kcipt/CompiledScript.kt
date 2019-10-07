package com.github.timaxinc.kcipt

open class CompiledScript(val source: Script, val rawClasses: Map<String, ByteArray>) : Script by source