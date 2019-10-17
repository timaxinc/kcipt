package com.github.timaxinc.kcipt.classloader

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.AnnotationSpec

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
}

private class WhitelistClassloaderTestDummyClass
