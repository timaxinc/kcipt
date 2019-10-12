package com.github.timaxinc.kcipt.compile.jvm

import com.github.timaxinc.kcipt.ScriptConfiguration
import com.github.timaxinc.kcipt.TextScript
import com.github.timaxinc.kcipt.result.onSuccess
import io.kotlintest.fail
import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

class KotlinJvmScriptCompilerTest : AnnotationSpec() {

    @Test
    suspend fun `simple script compilation`() {
        val script = TextScript("print(\"love ... shakreva.\uD83E\uDD84\")", ScriptConfiguration())
        val compiler = KotlinJvmScriptCompiler()
        val result = compiler(script)
        result.onSuccess {
            value.jvmRawClasses.entries.size shouldBe 1
            value.jvmRawClasses.entries.first().key shouldBe "Script"
            value.jvmRawClasses.entries.first().value.isNotEmpty() shouldBe true
            return
        }
        fail("shouldn't be reachable")
    }

    @Test
    suspend fun `simple script compilation with custom context`() {

    }
}
