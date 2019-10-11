package com.github.timaxinc.kcipt.compile

/**
 * can be returned by a [Compiler] in case of a compilation failure
 *
 * @property message
 */
open class CompilerReport(val message: String)
