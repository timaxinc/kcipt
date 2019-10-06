package com.github.timaxinc.kcipt.compile

interface Compiler {
    operator fun invoke(script: Script)
}