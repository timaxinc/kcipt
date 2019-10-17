package com.github.timaxinc.kcipt.util.factory

import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

class FactoryTest : AnnotationSpec() {

    @Test
    fun `TEST - factory with parameter`() {
        val factory = factory<String> { "unicorn" }

        factory.invoke() shouldBe "unicorn"
    }
}
