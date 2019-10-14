package com.github.timaxinc.kcipt.classloader

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.AnnotationSpec
import java.net.URL
import java.util.*

internal class BlockingClassloaderTest : AnnotationSpec() {

    @Test
    fun `FUNCTION loadClass(String) Class - Blacklist empty`() {
        val bcl = BlockingClassloader(listOf(), parent = BlockingClassloaderTest::class.java.classLoader)
        bcl.loadClass(BlockingClassloaderTestDummyClass::class.java.name) shouldBe BlockingClassloaderTestDummyClass::class.java
    }

    @Test
    fun `FUNCTION loadClass(String)  Class- Blacklist contains name of the requested Class`() {
        val bcl = BlockingClassloader(BlockingClassloaderTestDummyClass::class.java.name)
        shouldThrow<BlockingClassloader.ClassBlockedException> {
            bcl.loadClass(BlockingClassloaderTestDummyClass::class.java.name)
        }
    }

    @Test
    fun `FUNCTION loadClass(String) Class - Blacklist contains package of the requested Class`() {
        val bcl = BlockingClassloader("java.lang", parent = BlockingClassloaderTest::class.java.classLoader)
        shouldThrow<BlockingClassloader.PackageBlockedException> {
            bcl.loadClass(String::class.java.name)
        }
    }


    @Test
    fun `FUNCTION getResource(String) URL - No Blacklist`() {
        val bcl = BlockingClassloader(listOf(), BlockingClassloaderTestDummyLoader())
        bcl.getResource("dummyValue") shouldBe dummyUrl
    }

    @Test
    fun `FUNCTION getResource(String) URL - Resource on Blacklist`() {
        val bcl = BlockingClassloader(listOf("blockedDummyValue"), BlockingClassloaderTestDummyLoader())
        shouldThrow<BlockingClassloader.ResourceBlockedException> {
            bcl.getResource("blockedDummyValue")
        }
    }

    @Test
    fun `FUNCTION getResource(String) URL - Package on Blacklist`() {
        val bcl = BlockingClassloader("blocked.path", parent = BlockingClassloaderTestDummyLoader())
        shouldThrow<BlockingClassloader.PackageBlockedException> {
            bcl.getResource("blocked.path.DummyValue")
        }
    }


    @Test
    fun `FUNCTION getResources(String) Enumeration - No Blacklist`() {
        val bcl = BlockingClassloader(listOf(), parent = BlockingClassloaderTestDummyLoader())
        val resources = bcl.getResources("dummyValue")
        resources.nextElement() shouldBe URL("https://we.love.unicorns")
    }

    @Test
    fun `FUNCTION getResources(String) Enumeration - Resource on Blacklist`() {
        val bcl = BlockingClassloader("blockedDummyValue", parent = BlockingClassloaderTestDummyLoader())
        shouldThrow<BlockingClassloader.ResourceBlockedException> {
            bcl.getResources("blockedDummyValue")
        }
    }

    @Test
    fun `FUNCTION getResources(String) Enumeration - Package on Blacklist`() {
        val bcl = BlockingClassloader("blocked.path", parent = BlockingClassloaderTestDummyLoader())
        shouldThrow<BlockingClassloader.PackageBlockedException> {
            bcl.getResources("blocked.path.DummyValue")
        }
    }
}

private val dummyUrl = URL("https://i.am.mocked")

private class BlockingClassloaderTestDummyLoader : ClassLoader() {

    override fun loadClass(name: String?): Class<*> = BlockingClassloaderTestDummyClass()::class.java
    override fun getResource(name: String?): URL? = URL("https://i.am.mocked")
    override fun getResources(name: String?): Enumeration<URL> = BlockingClassloaderTestDummyEnumeration()
}

private class BlockingClassloaderTestDummyClass

private class BlockingClassloaderTestDummyEnumeration : Enumeration<URL> {

    override fun hasMoreElements(): Boolean {
        return true
    }

    override fun nextElement(): URL {
        return URL("https://we.love.unicorns")
    }
}
