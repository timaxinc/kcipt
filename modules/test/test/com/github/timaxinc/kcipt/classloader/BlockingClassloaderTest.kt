package com.github.timaxinc.kcipt.classloader

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.AnnotationSpec
import java.net.URL
import java.util.*

internal class BlockingClassloaderTest : AnnotationSpec() {

    @Test
    fun `loadClass(String) - Blacklist empty`() {
        val bcl = BlockingClassloader(listOf(), parent = BlockingClassloaderTest::class.java.classLoader)
        bcl.loadClass(BlockingClassloaderTestDummyClass::class.java.name) shouldBe BlockingClassloaderTestDummyClass::class.java
    }

    @Test
    fun `loadClass(String) - Blacklist contains name of the requested Class`() {
        val bcl = BlockingClassloader(BlockingClassloaderTestDummyClass::class.java.name)
        shouldThrow<BlockingClassloader.ClassBlockedException> {
            bcl.loadClass(BlockingClassloaderTestDummyClass::class.java.name)
        }
    }

    @Test
    fun `loadClass(String) - Blacklist contains package of the requested Class`() {
        val bcl = BlockingClassloader("java.lang", parent = BlockingClassloaderTest::class.java.classLoader)
        shouldThrow<BlockingClassloader.PackageBlockedException> {
            bcl.loadClass(String::class.java.name)
        }
    }


    @Test
    fun `getResource(String) - No Blacklist`() {
        val bcl = BlockingClassloader(listOf(), BlockingClassloaderTestDummyLoader())
        bcl.getResource("dummyValue") shouldBe dummyUrl
    }

    @Test
    fun `getResource(String) - Resource on Blacklist`() {
        val bcl = BlockingClassloader(listOf("blockedDummyValue"), BlockingClassloaderTestDummyLoader())
        shouldThrow<BlockingClassloader.ResourceBlockedException> {
            bcl.getResource("blockedDummyValue")
        }
    }

    @Test
    fun `getResource(String) - Package on Blacklist`() {
        val bcl = BlockingClassloader("blocked.path", parent = BlockingClassloaderTestDummyLoader())
        shouldThrow<BlockingClassloader.PackageBlockedException> {
            bcl.getResource("blocked.path.DummyValue")
        }
    }


    @Test
    fun `getResources(String) - No Blacklist`() {
        val bcl = BlockingClassloader(listOf(), parent = BlockingClassloaderTestDummyLoader())
        val resources = bcl.getResources("dummyValue")
        resources.nextElement() shouldBe URL("https://we.love.unicorns")
    }

    @Test
    fun `getResources(String) - Resource on Blacklist`() {
        val bcl = BlockingClassloader("blockedDummyValue", parent = BlockingClassloaderTestDummyLoader())
        shouldThrow<BlockingClassloader.ResourceBlockedException> {
            bcl.getResources("blockedDummyValue")
        }
    }

    @Test
    fun `getResources(String) - Package on Blacklist`() {
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
