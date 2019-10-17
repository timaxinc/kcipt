package com.github.timaxinc.kcipt.classloader

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.AnnotationSpec
import java.net.URL
import java.util.*

internal class BlacklistClassloaderTest : AnnotationSpec() {

    @Test
    fun `FUNCTION loadClass(String?) Class? - blacklist empty and softMode off`() {
        val bcl = BlacklistClassloader(false, parent = BlacklistClassloaderTest::class.java.classLoader)
        bcl.loadClass(BlacklistClassloaderTestDummyClass::class.java.name) shouldBe BlacklistClassloaderTestDummyClass::class.java
    }

    @Test
    fun `FUNCTION loadClass(String?) Class? - blacklist contains name of the requested class and softMode off`() {
        val bcl = BlacklistClassloader(false, BlacklistClassloaderTestDummyClass::class.java.name)
        shouldThrow<BlockingClassloader.ClassBlockedException> {
            bcl.loadClass(BlacklistClassloaderTestDummyClass::class.java.name)
        }
    }

    @Test
    fun `FUNCTION loadClass(String?) Class? - blacklist contains package of the requested Class and softMode off`() {
        val bcl = BlacklistClassloader(false, "java.lang", parent = BlacklistClassloaderTest::class.java.classLoader)
        shouldThrow<BlockingClassloader.PackageBlockedException> {
            bcl.loadClass(String::class.java.name)
        }
    }

    @Test
    fun `FUNCTION loadClass(String?) Class? - blacklist empty and softMode on`() {
        val bcl = BlacklistClassloader(true, parent = BlacklistClassloaderTest::class.java.classLoader)
        bcl.loadClass(BlacklistClassloaderTestDummyClass::class.java.name) shouldBe BlacklistClassloaderTestDummyClass::class.java
    }

    @Test
    fun `FUNCTION loadClass(String?) Class? - blacklist contains name of the requested class and softMode on`() {
        val bcl = BlacklistClassloader(true, BlacklistClassloaderTestDummyClass::class.java.name)
        bcl.loadClass(BlacklistClassloaderTestDummyClass::class.java.name) shouldBe null
    }

    @Test
    fun `FUNCTION loadClass(String?) Class? - blacklist contains package of the requested class and softMode on`() {
        val bcl = BlacklistClassloader(true, "java.lang", parent = BlacklistClassloaderTest::class.java.classLoader)
        bcl.loadClass(String::class.java.name) shouldBe null
    }


    @Test
    fun `FUNCTION getResource(String?) URL? - No Blacklist and softMode off`() {
        val bcl = BlacklistClassloader(false, parent = BlacklistClassloaderTestDummyLoader())
        bcl.getResource("dummyValue") shouldBe URL("https://i.am.mocked")
    }

    @Test
    fun `FUNCTION getResource(String?) URL? - Resource on Blacklist and softMode off`() {
        val bcl = BlacklistClassloader(false, listOf("blockedDummyValue"))
        shouldThrow<BlockingClassloader.ResourceBlockedException> {
            bcl.getResource("blockedDummyValue")
        }
    }

    @Test
    fun `FUNCTION getResource(String?) URL? - Package on Blacklist and softMode off`() {
        val bcl = BlacklistClassloader(false, "blocked.path")
        shouldThrow<BlockingClassloader.PackageBlockedException> {
            bcl.getResource("blocked.path.DummyValue")
        }
    }

    @Test
    fun `FUNCTION getResource(String)? URL? - No Blacklist and softMode on`() {
        val bcl = BlacklistClassloader(true, parent = BlacklistClassloaderTestDummyLoader())
        bcl.getResource("dummyValue") shouldBe URL("https://i.am.mocked")
    }

    @Test
    fun `FUNCTION getResource(String?) URL? - Resource on Blacklist and softMode on`() {
        val bcl = BlacklistClassloader(true, listOf("blockedDummyValue"), BlacklistClassloaderTestDummyLoader())
        bcl.getResource("blockedDummyValue") shouldBe null
    }

    @Test
    fun `FUNCTION getResource(String?) URL? - Package on Blacklist and softMode on`() {
        val bcl = BlacklistClassloader(true, "blocked.path", parent = BlacklistClassloaderTestDummyLoader())
        bcl.getResource("blocked.path.DummyValue") shouldBe null
    }


    @Test
    fun `FUNCTION getResources(String?) Enumeration(URL)? - No Blacklist and softMode off`() {
        val bcl = BlacklistClassloader(false, parent = BlacklistClassloaderTestDummyLoader())
        val resources = bcl.getResources("dummyValue")
        resources!!.nextElement() shouldBe URL("https://we.love.unicorns")
    }

    @Test
    fun `FUNCTION getResources(String?) Enumeration(URL)? - Resource on Blacklist and softMode off`() {
        val bcl = BlacklistClassloader(false, "blockedDummyValue", parent = BlacklistClassloaderTestDummyLoader())
        shouldThrow<BlockingClassloader.ResourceBlockedException> {
            bcl.getResources("blockedDummyValue")
        }
    }

    @Test
    fun `FUNCTION getResources(String?) Enumeration(URL)? - Package on Blacklist and softMode off`() {
        val bcl = BlacklistClassloader(false, "blocked.path", parent = BlacklistClassloaderTestDummyLoader())
        shouldThrow<BlockingClassloader.PackageBlockedException> {
            bcl.getResources("blocked.path.DummyValue")
        }
    }

    @Test
    fun `FUNCTION getResources(String?) Enumeration(URL)? - No Blacklist and softMode on`() {
        val bcl = BlacklistClassloader(true, parent = BlacklistClassloaderTestDummyLoader())
        val resources = bcl.getResources("dummyValue")
        resources!!.nextElement() shouldBe URL("https://we.love.unicorns")
    }

    @Test
    fun `FUNCTION getResources(String?) Enumeration(URL)? - Resource on Blacklist and softMode on`() {
        val bcl = BlacklistClassloader(true, "blockedDummyValue", parent = BlacklistClassloaderTestDummyLoader())
        bcl.getResources("blockedDummyValue") shouldBe null
    }

    @Test
    fun `FUNCTION getResources(String?) Enumeration(URL)? - Package on Blacklist and softMode on`() {
        val bcl = BlacklistClassloader(true, "blocked.path", parent = BlacklistClassloaderTestDummyLoader())
        bcl.getResources("blocked.path.DummyValue") shouldBe null
    }
}

private class BlacklistClassloaderTestDummyLoader : ClassLoader() {

    override fun loadClass(name: String?): Class<*> = BlacklistClassloaderTestDummyClass()::class.java
    override fun getResource(name: String?): URL? = URL("https://i.am.mocked")
    override fun getResources(name: String?): Enumeration<URL> = BlacklistClassloaderTestDummyEnumeration()
}

private class BlacklistClassloaderTestDummyClass

private class BlacklistClassloaderTestDummyEnumeration : Enumeration<URL> {

    override fun hasMoreElements(): Boolean {
        return true
    }

    override fun nextElement(): URL {
        return URL("https://we.love.unicorns")
    }
}
