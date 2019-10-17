package com.github.timaxinc.kcipt.classloader

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.AnnotationSpec
import java.net.URL
import java.util.*

internal class WhitelistClassloaderTest : AnnotationSpec() {
    @Test
    fun `FUNCTION loadClass(String?) Class? - no whitelist and softMode off`() {
        val wlc = WhitelistClassloader(false)
        shouldThrow<BlockingClassloader.ClassBlockedException> {
            wlc.loadClass("im.not.on.whitelist")
        }
    }

    @Test
    fun `FUNCTION loadClass(String?) Class? - class on whitelist and softMode off`() {
        val wlc = WhitelistClassloader(
                false,
                WhitelistClassloaderTestDummyClass::class.java.name,
                parent = WhitelistClassloaderTest::class.java.classLoader
        )
        wlc.loadClass(WhitelistClassloaderTestDummyClass::class.java.name) shouldBe WhitelistClassloaderTestDummyClass::class.java
    }

    @Test
    fun `FUNCTION loadClass(String?) Class? - package on whitelist and softMode off`() {
        val wlc = WhitelistClassloader(
                false,
                "com.github.timaxinc.kcipt.classloader",
                parent = WhitelistClassloaderTest::class.java.classLoader
        )
        wlc.loadClass(WhitelistClassloaderTestDummyClass::class.java.name) shouldBe WhitelistClassloaderTestDummyClass::class.java
    }

    @Test
    fun `FUNCTION loadClass(String?) Class? - no whitelist and softMode on`() {
        val wlc = WhitelistClassloader(true)
        wlc.loadClass("im.not.on.whitelist") shouldBe null
    }

    @Test
    fun `FUNCTION loadClass(String?) Class? - class on whitelist and softMode on`() {
        val wlc = WhitelistClassloader(
                true,
                WhitelistClassloaderTestDummyClass::class.java.name,
                parent = WhitelistClassloaderTest::class.java.classLoader
        )
        wlc.loadClass(WhitelistClassloaderTestDummyClass::class.java.name) shouldBe WhitelistClassloaderTestDummyClass::class.java
    }

    @Test
    fun `FUNCTION loadClass(String?) Class? - package on whitelist and softMode on`() {
        val wlc = WhitelistClassloader(
                true, "com.github.timaxinc.kcipt.classloader", parent = WhitelistClassloaderTest::class.java.classLoader
        )
        wlc.loadClass(WhitelistClassloaderTestDummyClass::class.java.name) shouldBe WhitelistClassloaderTestDummyClass::class.java
    }


    @Test
    fun `FUNCTION getResource(String?) URL? - no whitelist and softMode off`() {
        val wlc = WhitelistClassloader(false)
        shouldThrow<BlockingClassloader.ResourceBlockedException> {
            wlc.getResource("notOnWhitelist")
        }
    }

    @Test
    fun `FUNCTION getResource(String?) URL? - resource on whitelist and softMode off`() {
        val wlc = WhitelistClassloader(false, "whitelistedResource", parent = WhitelistClassloaderTestDummyLoader())
        wlc.getResource("whitelistedResource") shouldBe dummyUrl
    }

    @Test
    fun `FUNCTION getResource(String?) URL? - package on whitelist and softMode off`() {
        val wlc = WhitelistClassloader(false, "whitelisted", parent = WhitelistClassloaderTestDummyLoader())
        wlc.getResource("whitelisted.Resource") shouldBe dummyUrl
    }

    @Test
    fun `FUNCTION getResource(String?) URL? - no whitelist and softMode on`() {
        val wlc = WhitelistClassloader(true)
        wlc.getResource("notOnWhitelist") shouldBe null
    }

    @Test
    fun `FUNCTION getResource(String?) URL? - resource on whitelist and softMode on`() {
        val wlc = WhitelistClassloader(true, "whitelistedResource", parent = WhitelistClassloaderTestDummyLoader())
        wlc.getResource("whitelistedResource") shouldBe dummyUrl
    }

    @Test
    fun `FUNCTION getResource(String?) URL? - package on whitelist and softMode on`() {
        val wlc = WhitelistClassloader(true, "whitelisted", parent = WhitelistClassloaderTestDummyLoader())
        wlc.getResource("whitelisted.Resource") shouldBe dummyUrl
    }
}

private class WhitelistClassloaderTestDummyClass

private val dummyUrl = URL("https://i.am.mocked")

private class WhitelistClassloaderTestDummyLoader : ClassLoader() {

    override fun loadClass(name: String?): Class<*> = WhitelistClassloaderTestDummyClass()::class.java
    override fun getResource(name: String?): URL? = URL("https://i.am.mocked")
    override fun getResources(name: String?): Enumeration<URL> = WhitelistClassloaderTestDummyEnumeration()
}

private class WhitelistClassloaderTestDummyEnumeration : Enumeration<URL> {

    override fun hasMoreElements(): Boolean {
        return true
    }

    override fun nextElement(): URL {
        return URL("https://we.love.unicorns")
    }
}