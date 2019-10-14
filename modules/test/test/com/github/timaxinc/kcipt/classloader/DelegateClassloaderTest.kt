package com.github.timaxinc.kcipt.classloader

import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec
import java.net.URL
import java.util.*

class DelegateClassloaderTest : AnnotationSpec() {

    @Test
    fun `loadClass(String) - delegate has Class`() {
        val dcl = DelegateClassloader(DummyDelegate(), DummyParent())
        dcl.loadClass("have") shouldBe DummyDelegateClass::class.java
    }

    @Test
    fun `loadClass(String) - delegate does not have Class`() {
        val dcl = DelegateClassloader(DummyDelegate(), DummyParent())
        dcl.loadClass("doesn't have") shouldBe DummyParentClass::class.java
    }


    @Test
    fun `getResource(String) - delegate has resource`() {
        val dcl = DelegateClassloader(DummyDelegate(), DummyParent())
        dcl.getResource("have") shouldBe URL("https://delegate.mock")
    }

    @Test
    fun `getResource(String) - delegate does not have resource`() {
        val dcl = DelegateClassloader(DummyDelegate(), DummyParent())
        dcl.getResource("doesn't have") shouldBe URL("https://parent.mock")
    }


    @Test
    fun `getResources(String) - delegate has resource`() {
        val dcl = DelegateClassloader(DummyDelegate(), DummyParent())
        val resources = dcl.getResources("have")
        resources.nextElement() shouldBe URL("https://delegate.unicorn")
    }

    @Test
    fun `getResources(String) - delegate does not have resource`() {
        val dcl = DelegateClassloader(DummyDelegate(), DummyParent())
        val resources = dcl.getResources("doesn't have")
        resources.nextElement() shouldBe URL("https://parent.unicorn")
    }
}

internal class DummyDelegate : ClassLoader() {

    override fun loadClass(name: String?): Class<*> {
        if (name == "have") return DummyDelegateClass()::class.java
        else throw ClassNotFoundException(name)
    }

    override fun getResource(name: String?): URL? {
        return if (name == "have") URL("https://delegate.mock")
        else null
    }

    override fun getResources(name: String?): Enumeration<URL> {
        return if (name == "have") DummyDelegateEnumeration()
        else EmptyEnumeration()
    }
}

private class DummyParent : ClassLoader() {

    override fun loadClass(name: String?): Class<*> = DummyParentClass()::class.java
    override fun getResource(name: String?): URL? = URL("https://parent.mock")
    override fun getResources(name: String?): Enumeration<URL> = DummyParentEnumeration()
}

private class DummyDelegateClass
private class DummyParentClass

private class EmptyEnumeration : Enumeration<URL> {

    override fun hasMoreElements(): Boolean {
        return false
    }

    override fun nextElement(): URL {
        throw NoSuchElementException()
    }
}

private class DummyDelegateEnumeration : Enumeration<URL> {

    override fun hasMoreElements(): Boolean {
        return true
    }

    override fun nextElement(): URL {
        return URL("https://delegate.unicorn")
    }
}

private class DummyParentEnumeration : Enumeration<URL> {

    override fun hasMoreElements(): Boolean {
        return true
    }

    override fun nextElement(): URL {
        return URL("https://parent.unicorn")
    }
}
