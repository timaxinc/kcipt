package com.github.timaxinc.kcipt.classloader

import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

internal class ByteClassloaderTest : AnnotationSpec() {
    @Test
    fun `FUNCTION findClass(String) - class in class defs`() {
        val classPath = "${ByteClassloaderTestDummyClass::class.qualifiedName!!.replace(".", "/")}.class"
        val dummyBytecode = ByteClassloaderTestDummyClass::class.java.classLoader.getResource(classPath)!!.readBytes()
        val bcl = ByteClassloader(mapOf(ByteClassloaderTestDummyClass::class.qualifiedName!! to dummyBytecode))
        bcl.loadClass(ByteClassloaderTestDummyClass::class.qualifiedName).kotlin.qualifiedName shouldBe ByteClassloaderTestDummyClass::class.qualifiedName
    }
}

private class ByteClassloaderTestDummyClass
