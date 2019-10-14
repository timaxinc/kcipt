package com.github.timaxinc.kcipt.classloader

import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec
import java.net.URL
import java.util.*

class DelegateClassloaderTest : AnnotationSpec() {

    @Test
    fun `FUNCTION loadClass(String) Class - delegate has Class`() {
        val dcl = DelegateClassloader(DelegateClassloaderTestDummyDelegate(), DelegateClassloaderTestDummyParent())
        dcl.loadClass("have") shouldBe DelegateClassloaderTestDummyDelegateClass::class.java
    }

    @Test
    fun `FUNCTION loadClass(String) Class - delegate does not have Class`() {
        val dcl = DelegateClassloader(DelegateClassloaderTestDummyDelegate(), DelegateClassloaderTestDummyParent())
        dcl.loadClass("doesn't have") shouldBe DelegateClassloaderTestDummyParentClass::class.java
    }


    @Test
    fun `FUNCTION getResource(String) URL - delegate has resource`() {
        val dcl = DelegateClassloader(DelegateClassloaderTestDummyDelegate(), DelegateClassloaderTestDummyParent())
        dcl.getResource("have") shouldBe URL("https://delegate.mock")
    }

    @Test
    fun `FUNCTION getResource(String) URL - delegate does not have resource`() {
        val dcl = DelegateClassloader(DelegateClassloaderTestDummyDelegate(), DelegateClassloaderTestDummyParent())
        dcl.getResource("doesn't have") shouldBe URL("https://parent.mock")
    }


    @Test
    fun `FUNCTION getResources(String) Enumeration - delegate has resource`() {
        val dcl = DelegateClassloader(DelegateClassloaderTestDummyDelegate(), DelegateClassloaderTestDummyParent())
        val resources = dcl.getResources("have")
        resources.nextElement() shouldBe URL("https://delegate.unicorn")
    }

    @Test
    fun `FUNCTION getResources(String) Enumeration - delegate does not have resource`() {
        val dcl = DelegateClassloader(DelegateClassloaderTestDummyDelegate(), DelegateClassloaderTestDummyParent())
        val resources = dcl.getResources("doesn't have")
        resources.nextElement() shouldBe URL("https://parent.unicorn")
    }
}

internal class DelegateClassloaderTestDummyDelegate : ClassLoader() {

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

private class DelegateClassloaderTestDummyParent : ClassLoader() {

    override fun loadClass(name: String?): Class<*> = DelegateClassloaderTestDummyParentClass()::class.java
    override fun getResource(name: String?): URL? = URL("https://parent.mock")
    override fun getResources(name: String?): Enumeration<URL> = DelegateClassloaderTestDummyParentEnumeration()
}

private class DelegateClassloaderTestDummyDelegateClass
private class DelegateClassloaderTestDummyParentClass

private class DelegateClassloaderTestEmptyEnumeration : Enumeration<URL> {

    override fun hasMoreElements(): Boolean {
        return false
    }

    override fun nextElement(): URL {
        throw NoSuchElementException()
    }
}

private class DelegateClassloaderTestDummyDelegateEnumeration : Enumeration<URL> {

    override fun hasMoreElements(): Boolean {
        return true
    }

    override fun nextElement(): URL {
        return URL("https://delegate.unicorn")
    }
}

private class DelegateClassloaderTestDummyParentEnumeration : Enumeration<URL> {

    override fun hasMoreElements(): Boolean {
        return true
    }

    override fun nextElement(): URL {
        return URL("https://parent.unicorn")
    }
}
