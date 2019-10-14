package com.github.timaxinc.kcipt.classloader

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.AnnotationSpec
import java.net.URL
import java.util.*

internal class BlockingClassloaderTest : AnnotationSpec() {

    @Test
    fun `loadClass(String) - Blacklist empty`() {
        val bcl = BlockingClassloader(BlockingClassloaderTest::class.java.classLoader)
        bcl.loadClass(DelegateClassloaderTestDummyClass::class.java.name) shouldBe DelegateClassloaderTestDummyClass::class.java
    }

    @Test
    fun `loadClass(String) - Blacklist contains name of the requested Class`() {
        val bcl =
                BlockingClassloader(
                        BlockingClassloaderTest::class.java.classLoader,
                        DelegateClassloaderTestDummyClass::class.java.name
                )
        shouldThrow<BlockingClassloader.ClassBlockedException> {
            bcl.loadClass(DelegateClassloaderTestDummyClass::class.java.name)
        }
    }

    @Test
    fun `loadClass(String) - Blacklist contains package of the requested Class`() {
        val bcl = BlockingClassloader(BlockingClassloaderTest::class.java.classLoader, "java.lang")
        shouldThrow<BlockingClassloader.PackageBlockedException> {
            bcl.loadClass(String::class.java.name)
        }
    }


    @Test
    fun `getResource(String) - No Blacklist`() {
        val bcl = BlockingClassloader(DelegateClassloaderTestDummyLoader())
        bcl.getResource("dummyValue") shouldBe dummyUrl
    }

    @Test
    fun `getResource(String) - Resource on Blacklist`() {
        val bcl = BlockingClassloader(DelegateClassloaderTestDummyLoader(), "blockedDummyValue")
        shouldThrow<BlockingClassloader.ResourceBlockedException> {
            bcl.getResource("blockedDummyValue")
        }
    }

    @Test
    fun `getResource(String) - Package on Blacklist`() {
        val bcl = BlockingClassloader(DelegateClassloaderTestDummyLoader(), "blocked.path")
        shouldThrow<BlockingClassloader.PackageBlockedException> {
            bcl.getResource("blocked.path.DummyValue")
        }
    }


    @Test
    fun `getResources(String) - No Blacklist`() {
        val bcl = BlockingClassloader(DelegateClassloaderTestDummyLoader())
        val resources = bcl.getResources("dummyValue")
        resources.nextElement() shouldBe URL("https://we.love.unicorns")
    }

    @Test
    fun `getResources(String) - Resource on Blacklist`() {
        val bcl = BlockingClassloader(DelegateClassloaderTestDummyLoader(), "blockedDummyValue")
        shouldThrow<BlockingClassloader.ResourceBlockedException> {
            bcl.getResources("blockedDummyValue")
        }
    }

    @Test
    fun `getResources(String) - Package on Blacklist`() {
        val bcl = BlockingClassloader(DelegateClassloaderTestDummyLoader(), "blocked.path")
        shouldThrow<BlockingClassloader.PackageBlockedException> {
            bcl.getResources("blocked.path.DummyValue")
        }
    }
}

internal val dummyUrl = URL("https://i.am.mocked")

internal class DelegateClassloaderTestDummyLoader : ClassLoader() {

    override fun loadClass(name: String?): Class<*> = DelegateClassloaderTestDummyClass()::class.java
    override fun getResource(name: String?): URL? = URL("https://i.am.mocked")
    override fun getResources(name: String?): Enumeration<URL> = DelegateClassloaderTestDummyEnumeration()
}

internal class DelegateClassloaderTestDummyClass

internal class DelegateClassloaderTestDummyEnumeration : Enumeration<URL> {

    override fun hasMoreElements(): Boolean {
        return true
    }

    override fun nextElement(): URL {
        return URL("https://we.love.unicorns")
    }
}
