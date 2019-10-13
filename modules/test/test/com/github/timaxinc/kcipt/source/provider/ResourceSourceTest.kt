package com.github.timaxinc.kcipt.source.provider

import com.github.timaxinc.kcipt.util.io.Resource
import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

internal class ResourceSourceTest : AnnotationSpec() {

    @Test
    fun `FUNCTION - read content of file virtual`() {
        val resourcePath = ResourceSourceTest::class.qualifiedName?.replace(".", "/") + ".class"
        val resource = Resource(resourcePath)
        val resourceContend = resource.readText()

        val resourceSource = ResourceSource(resourcePath)
        resourceSource.read() shouldBe resourceContend
    }
}
