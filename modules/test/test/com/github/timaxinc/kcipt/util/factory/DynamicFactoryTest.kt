package com.github.timaxinc.kcipt.util.factory

import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

class DynamicFactoryTest : AnnotationSpec() {

    @Test
    fun `TEST - factory with parameter`() {
        val factory = dynamicFactory<String, String> { it }

        factory.invoke("unicorn") shouldBe "unicorn"
    }
}
