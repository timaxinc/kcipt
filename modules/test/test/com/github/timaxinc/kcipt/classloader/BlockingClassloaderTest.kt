package com.github.timaxinc.kcipt.classloader

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.AnnotationSpec
import java.net.URL
import java.util.*

internal class BlockingClassloaderTest : AnnotationSpec() {

    @Test
    fun `loadClass - Blacklist empty`() {
        val bcl = BlockingClassloader(BlockingClassloaderTest::class.java.classLoader)
        bcl.loadClass(DummyClass::class.java.name) shouldBe DummyClass::class.java
    }

    @Test
    fun `loadClass - Blacklist contains name of the requested Class`() {
        val bcl = BlockingClassloader(BlockingClassloaderTest::class.java.classLoader, DummyClass::class.java.name)
        shouldThrow<BlockingClassloader.ClassBlockedException> {
            bcl.loadClass(DummyClass::class.java.name)
        }
    }

    @Test
    fun `loadClass - Blacklist contains package of the requested Class`() {
        val bcl = BlockingClassloader(BlockingClassloaderTest::class.java.classLoader, "java.lang")
        shouldThrow<BlockingClassloader.PackageBlockedException> {
            bcl.loadClass(String::class.java.name)
        }
    }
}

class DummyLoader : ClassLoader() {
    override fun loadClass(name: String?): Class<*> = DummyClass()::class.java
    override fun getResource(name: String?): URL? = URL("i/am/mocked")
    override fun getResources(name: String?): Enumeration<URL> = DummyEnumeration()
}

class DummyClass

class DummyEnumeration : Enumeration<URL> {
    override fun hasMoreElements(): Boolean {
        return true
    }

    override fun nextElement(): URL {
        return URL("we/love/unicorns")
    }

}
