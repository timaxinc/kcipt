package com.github.timaxinc.kcipt

/**
 * implementation of [Script] that gets sourcecode from [String] [text]
 *
 * @property text sourcecode of [Script]
 * @property configuration [ScriptConfiguration] of [Script]
 */
class TextScript(override val text: String, override val configuration: ScriptConfiguration) : Script
