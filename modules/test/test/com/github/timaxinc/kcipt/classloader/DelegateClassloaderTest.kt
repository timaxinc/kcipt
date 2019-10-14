package com.github.timaxinc.kcipt.classloader

import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec
import java.net.URL
import java.util.*

class DelegateClassloaderTest : AnnotationSpec() {

    @Test
    fun `loadClass(String) - delegate has Class`() {
        val dcl = DelegateClassloader(DelegateClassloaderTestDummyParent(), DummyDelegate())
        dcl.loadClass("have") shouldBe DelegateClassloaderTestDummyDelegateClass::class.java
    }

    @Test
    fun `loadClass(String) - delegate does not have Class`() {
        val dcl = DelegateClassloader(DelegateClassloaderTestDummyParent(), DummyDelegate())
        dcl.loadClass("doesn't have") shouldBe DelegateClassloaderTestDummyParentClass::class.java
    }


    @Test
    fun `getResource(String) - delegate has resource`() {
        val dcl = DelegateClassloader(DelegateClassloaderTestDummyParent(), DummyDelegate())
        dcl.getResource("have") shouldBe URL("https://delegate.mock")
    }

    @Test
    fun `getResource(String) - delegate does not have resource`() {
        val dcl = DelegateClassloader(DelegateClassloaderTestDummyParent(), DummyDelegate())
        dcl.getResource("doesn't have") shouldBe URL("https://parent.mock")
    }


    @Test
    fun `getResources(String) - delegate has resource`() {
        val dcl = DelegateClassloader(DelegateClassloaderTestDummyParent(), DummyDelegate())
        val resources = dcl.getResources("have")
        resources.nextElement() shouldBe URL("https://delegate.unicorn")
    }

    @Test
    fun `getResources(String) - delegate does not have resource`() {
        val dcl = DelegateClassloader(DelegateClassloaderTestDummyParent(), DummyDelegate())
        val resources = dcl.getResources("doesn't have")
        resources.nextElement() shouldBe URL("https://parent.unicorn")
    }
}

internal class DummyDelegate : ClassLoader() {

    override fun loadClass(name: String?): Class<*> {
        if (name == "have") return DelegateClassloaderTestDummyDelegateClass()::class.java
        else throw ClassNotFoundException(name)
    }

    override fun getResource(name: String?): URL? {
        return if (name == "have") URL("https://delegate.mock")
        else null
    }

    override fun getResources(name: String?): Enumeration<URL> {
        return if (name == "have") DelegateClassloaderTestDummyDelegateEnumeration()
        else DelegateClassloaderTestEmptyEnumeration()
    }
}

internal class DelegateClassloaderTestDummyParent : ClassLoader() {

    override fun loadClass(name: String?): Class<*> = DelegateClassloaderTestDummyParentClass()::class.java
    override fun getResource(name: String?): URL? = URL("https://parent.mock")
    override fun getResources(name: String?): Enumeration<URL> = DelegateClassloaderTestDummyParentEnumeration()
}

internal class DelegateClassloaderTestDummyDelegateClass
internal class DelegateClassloaderTestDummyParentClass

internal class DelegateClassloaderTestEmptyEnumeration : Enumeration<URL> {

    override fun hasMoreElements(): Boolean {
        return false
    }

    override fun nextElement(): URL {
        throw NoSuchElementException()
    }
}

internal class DelegateClassloaderTestDummyDelegateEnumeration : Enumeration<URL> {

    override fun hasMoreElements(): Boolean {
        return true
    }

    override fun nextElement(): URL {
        return URL("https://delegate.unicorn")
    }
}

internal class DelegateClassloaderTestDummyParentEnumeration : Enumeration<URL> {

    override fun hasMoreElements(): Boolean {
        return true
    }

    override fun nextElement(): URL {
        return URL("https://parent.unicorn")
    }
}
