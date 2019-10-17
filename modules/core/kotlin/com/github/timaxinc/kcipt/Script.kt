package com.github.timaxinc.kcipt

/**
 * representation of a script
 *
 * @property text the script text
 * @property configuration configures script compilation and execution
 */
interface Script {

    val text: String
    val configuration: ScriptConfiguration
}
