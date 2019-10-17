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
        wlc.getResource("whitelistedResource") shouldBe URL("https://i.am.mocked")
    }

    @Test
    fun `FUNCTION getResource(String?) URL? - package on whitelist and softMode off`() {
        val wlc = WhitelistClassloader(false, "whitelisted", parent = WhitelistClassloaderTestDummyLoader())
        wlc.getResource("whitelisted.Resource") shouldBe URL("https://i.am.mocked")
    }

    @Test
    fun `FUNCTION getResource(String?) URL? - no whitelist and softMode on`() {
        val wlc = WhitelistClassloader(true)
        wlc.getResource("notOnWhitelist") shouldBe null
    }

    @Test
    fun `FUNCTION getResource(String?) URL? - resource on whitelist and softMode on`() {
        val wlc = WhitelistClassloader(true, "whitelistedResource", parent = WhitelistClassloaderTestDummyLoader())
        wlc.getResource("whitelistedResource") shouldBe URL("https://i.am.mocked")
    }

    @Test
    fun `FUNCTION getResource(String?) URL? - package on whitelist and softMode on`() {
        val wlc = WhitelistClassloader(true, "whitelisted", parent = WhitelistClassloaderTestDummyLoader())
        wlc.getResource("whitelisted.Resource") shouldBe URL("https://i.am.mocked")
    }


    @Test
    fun `FUNCTION getResources(String?) URL? - no whitelist and softMode off`() {
        val wlc = WhitelistClassloader(false)
        shouldThrow<BlockingClassloader.ResourceBlockedException> {
            wlc.getResources("blocked")
        }
    }

    @Test
    fun `FUNCTION getResources(String?) URL? - resource on whitelist and softMode off`() {
        val wlc = WhitelistClassloader(false, "allowed", parent = WhitelistClassloaderTestDummyLoader())
        val resources = wlc.getResources("allowed")
        resources!!.nextElement() shouldBe URL("https://we.love.unicorns")
    }

    @Test
    fun `FUNCTION getResources(String?) URL? - package on whitelist and softMode off`() {
        val wlc = WhitelistClassloader(false, "package", parent = WhitelistClassloaderTestDummyLoader())
        val resources = wlc.getResources("package.allowed")
        resources!!.nextElement() shouldBe URL("https://we.love.unicorns")
    }

    @Test
    fun `FUNCTION getResources(String?) URL? - no whitelist and softMode on`() {
        val wlc = WhitelistClassloader(true)
        wlc.getResources("blocked") shouldBe null
    }

    @Test
    fun `FUNCTION getResources(String?) URL? - resource on whitelist and softMode on`() {
        val wlc = WhitelistClassloader(true, "allowed", parent = WhitelistClassloaderTestDummyLoader())
        val resources = wlc.getResources("allowed")
        resources!!.nextElement() shouldBe URL("https://we.love.unicorns")
    }

    @Test
    fun `FUNCTION getResources(String?) URL? - package on whitelist and softMode on`() {
        val wlc = WhitelistClassloader(true, "package", parent = WhitelistClassloaderTestDummyLoader())
        val resources = wlc.getResources("package.allowed")
        resources!!.nextElement() shouldBe URL("https://we.love.unicorns")
    }
}

private class WhitelistClassloaderTestDummyClass

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