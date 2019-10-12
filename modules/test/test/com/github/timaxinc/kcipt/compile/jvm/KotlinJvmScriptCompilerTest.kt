package com.github.timaxinc.kcipt.compile.jvm

import ScriptContext
import com.github.timaxinc.kcipt.ScriptConfiguration
import com.github.timaxinc.kcipt.TextScript
import com.github.timaxinc.kcipt.contextClass
import com.github.timaxinc.kcipt.result.onFailure
import com.github.timaxinc.kcipt.result.onSuccess
import com.github.timaxinc.kcipt.test.notReachable
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

class KotlinJvmScriptCompilerTest : AnnotationSpec() {

    val simpleTestScriptSourcecode = "print(\"love ... shakreva.\uD83E\uDD84\")"
    val simpleTestScriptSourcecodeWithSyntaxError = "print(\"love ... shakreva.\uD83E\uDD84\""

    @Test
    suspend fun `INT - compile script on jvm with default configuration`() {
        val script = TextScript(simpleTestScriptSourcecode, ScriptConfiguration { })
        val compiler = KotlinJvmScriptCompiler()
        val result = compiler(script)
        result.onSuccess {
            value.jvmRawClasses.entries.size shouldBe 1
            value.jvmRawClasses.entries.first().key shouldBe "Script"
            value.jvmRawClasses.entries.first().value.isNotEmpty() shouldBe true
            return
        }
        notReachable()
    }

    @Test
    suspend fun `INT - compile script with syntax error on jvm with default configuration`() {
        val script = TextScript(simpleTestScriptSourcecodeWithSyntaxError, ScriptConfiguration { })
        val compiler = KotlinJvmScriptCompiler()
        val result = compiler(script)
        result.onFailure {
            //TODO only count error level reports
            reports.size shouldBe 3
        }
        result.onSuccess {
            notReachable()
        }
    }

    @Test
    suspend fun `INT - compile script on jvm with custom script context`() {
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
        notReachable()
    }
}

private class UnicornDummyScriptContext : ScriptContext
