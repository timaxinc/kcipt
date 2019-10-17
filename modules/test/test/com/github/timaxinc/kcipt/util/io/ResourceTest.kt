package com.github.timaxinc.kcipt.util.io

import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

class ResourceTest : AnnotationSpec() {

    @Test
    fun `TEST - check resource wrapping fields`() {
        val resourcePath = ResourceTest::class.qualifiedName?.replace(".", "/") + ".class"
        val resourceUrl = ResourceTest::class.java.getResource("/$resourcePath")
        val resourceAsStream = ResourceTest::class.java.getResourceAsStream("/$resourcePath")

        val resource = Resource(resourcePath, ResourceTest::class)

        resource.context shouldBe ResourceTest::class
        resource.path shouldBe "/$resourcePath"
        resource.url shouldBe resourceUrl
        resource.uri shouldBe resourceUrl.toURI()
        resource.externalForm shouldBe resourceUrl.toExternalForm()
        resource.stream.readAllBytes() shouldBe resourceAsStream.readAllBytes()
    }

    @Test
    fun `TEST - read from Resource`() {
        val resourcePath = ResourceTest::class.qualifiedName?.replace(".", "/") + ".class"
        val resourceUrl = ResourceTest::class.java.getResource("/$resourcePath")

        val resource = Resource(resourcePath, ResourceTest::class)

        resource.readText() shouldBe resourceUrl.readText()
    }
}
