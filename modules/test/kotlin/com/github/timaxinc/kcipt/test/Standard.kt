package com.github.timaxinc.kcipt.test

import io.kotlintest.fail

fun notReachable(): Nothing = fail("shouldn't be reachable")
