package com.github.timaxinc.kcipt.compile

import com.github.timaxinc.kcipt.Script

interface Compiler {
    operator fun invoke(script: Script)
}