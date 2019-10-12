package com.github.timaxinc.kcipt.source

import com.github.timaxinc.kcipt.source.provider.mutableSource
import com.github.timaxinc.kcipt.source.provider.source
import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

class LambdaSourcesTest : AnnotationSpec() {

    @Test
    fun `read from source`() {
        val value = "unicorn"
        val source = source {
            value
        }
        source.read() shouldBe value
    }

    @Test
    fun `read and write from and to source`() {
        var value = "unicorn"
        val source = mutableSource(readBlock = {
            value
        }, writeBlock = {
            value = this
        })
        source.read() shouldBe value
        source.write("hello galaxy")
        value shouldBe "hello galaxy"
    }
}
