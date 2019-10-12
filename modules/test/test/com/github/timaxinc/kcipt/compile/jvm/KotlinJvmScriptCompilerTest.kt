package com.github.timaxinc.kcipt.compile.jvm

import ScriptContext
import com.github.timaxinc.kcipt.ScriptConfiguration
import com.github.timaxinc.kcipt.TextScript
import com.github.timaxinc.kcipt.contextClass
import com.github.timaxinc.kcipt.result.onFailure
import com.github.timaxinc.kcipt.result.onSuccess
import io.kotlintest.fail
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

class KotlinJvmScriptCompilerTest : AnnotationSpec() {

    val simpleTestScriptSourcecode = "print(\"love ... shakreva.\uD83E\uDD84\")"
    val simpleTestScriptSourcecodeWithSyntaxError = "print(\"love ... shakreva.\uD83E\uDD84\""

    @Test
    suspend fun `simple script compilation`() {
        val script = TextScript(simpleTestScriptSourcecode, ScriptConfiguration { })
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
    suspend fun `simple script compilation with syntax error`() {
        val script = TextScript(simpleTestScriptSourcecodeWithSyntaxError, ScriptConfiguration { })
        val compiler = KotlinJvmScriptCompiler()
        val result = compiler(script)
        result.onFailure {
            //TODO only count error level reports
            reports.size shouldBe 3
        }
        result.onSuccess {
            fail("shouldn't be reachable")
        }
    }

    @Test
    suspend fun `simple script compilation with custom context`() {
        val script = TextScript(simpleTestScriptSourcecode, ScriptConfiguration {
            contextClass = UnicornDummyScriptContext::class
        })
        val compiler = KotlinJvmScriptCompiler()
        val result = compiler(script)
        result.onSuccess {
            value.jvmRawClasses.entries.size shouldBe 1
            value.jvmRawClasses.entries.first().key shouldBe "Script"
            value.jvmRawClasses.entries.first().value.isNotEmpty() shouldBe true
            val scriptBytecodeAsString = String(value.jvmRawClasses.entries.first().value)
            val qualifiedNameOfUnicornDummyScriptContextInBytecode = run {
                UnicornDummyScriptContext::class.qualifiedName!!.replace(".", "/")
            }
            scriptBytecodeAsString shouldContain qualifiedNameOfUnicornDummyScriptContextInBytecode
            return
        }
        fail("shouldn't be reachable")
    }
}

private class UnicornDummyScriptContext : ScriptContext
