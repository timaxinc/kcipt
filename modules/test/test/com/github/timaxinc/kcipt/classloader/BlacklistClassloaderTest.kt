package com.github.timaxinc.kcipt.classloader

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.AnnotationSpec
import java.net.URL
import java.util.*

internal class BlacklistClassloaderTest : AnnotationSpec() {

    @Test
    fun `FUNCTION loadClass(String) Class - blacklist empty and softMode off`() {
        val bcl = BlacklistClassloader(false, parent = BlacklistClassloaderTest::class.java.classLoader)
        bcl.loadClass(BlockingClassloaderTestDummyClass::class.java.name) shouldBe BlockingClassloaderTestDummyClass::class.java
    }

    @Test
    fun `FUNCTION loadClass(String) Class - blacklist contains name of the requested class and softMode off`() {
        val bcl = BlacklistClassloader(false, BlockingClassloaderTestDummyClass::class.java.name)
        shouldThrow<BlockingClassloader.ClassBlockedException> {
            bcl.loadClass(BlockingClassloaderTestDummyClass::class.java.name)
        }
    }

    @Test
    fun `FUNCTION loadClass(String) Class - blacklist contains package of the requested Class and softMode off`() {
        val bcl = BlacklistClassloader(false, "java.lang", parent = BlacklistClassloaderTest::class.java.classLoader)
        shouldThrow<BlockingClassloader.PackageBlockedException> {
            bcl.loadClass(String::class.java.name)
        }
    }

    @Test
    fun `FUNCTION loadClass(String) Class - blacklist empty and softMode on`() {
        val bcl = BlacklistClassloader(true, parent = BlacklistClassloaderTest::class.java.classLoader)
        bcl.loadClass(BlockingClassloaderTestDummyClass::class.java.name) shouldBe BlockingClassloaderTestDummyClass::class.java
    }

    @Test
    fun `FUNCTION loadClass(String) Class - blacklist contains name of the requested class and softMode on`() {
        val bcl = BlacklistClassloader(true, BlockingClassloaderTestDummyClass::class.java.name)
        bcl.loadClass(BlockingClassloaderTestDummyClass::class.java.name) shouldBe null
    }

    @Test
    fun `FUNCTION loadClass(String) Class - blacklist contains package of the requested class and softMode on`() {
        val bcl = BlacklistClassloader(true, "java.lang", parent = BlacklistClassloaderTest::class.java.classLoader)
        bcl.loadClass(String::class.java.name) shouldBe null
    }


    @Test
    fun `FUNCTION getResource(String) URL - No Blacklist`() {
        val bcl = BlacklistClassloader(false, parent = BlockingClassloaderTestDummyLoader())
        bcl.getResource("dummyValue") shouldBe dummyUrl
    }

    @Test
    fun `FUNCTION getResource(String) URL - Resource on Blacklist`() {
        val bcl = BlacklistClassloader(false, listOf("blockedDummyValue"), BlockingClassloaderTestDummyLoader())
        shouldThrow<BlockingClassloader.ResourceBlockedException> {
            bcl.getResource("blockedDummyValue")
        }
    }

    @Test
    fun `FUNCTION getResource(String) URL - Package on Blacklist`() {
        val bcl = BlacklistClassloader(false, "blocked.path", parent = BlockingClassloaderTestDummyLoader())
        shouldThrow<BlockingClassloader.PackageBlockedException> {
            bcl.getResource("blocked.path.DummyValue")
        }
    }


    @Test
    fun `FUNCTION getResources(String) Enumeration(URL) - No Blacklist`() {
        val bcl = BlacklistClassloader(false, parent = BlockingClassloaderTestDummyLoader())
        val resources = bcl.getResources("dummyValue")
        resources!!.nextElement() shouldBe URL("https://we.love.unicorns")
    }

    @Test
    fun `FUNCTION getResources(String) Enumeration(URL) - Resource on Blacklist`() {
        val bcl = BlacklistClassloader(false, "blockedDummyValue", parent = BlockingClassloaderTestDummyLoader())
        shouldThrow<BlockingClassloader.ResourceBlockedException> {
            bcl.getResources("blockedDummyValue")
        }
    }

    @Test
    fun `FUNCTION getResources(String) Enumeration(URL) - Package on Blacklist`() {
        val bcl = BlacklistClassloader(false, "blocked.path", parent = BlockingClassloaderTestDummyLoader())
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
