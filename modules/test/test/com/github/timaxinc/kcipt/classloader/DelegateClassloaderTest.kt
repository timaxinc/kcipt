package com.github.timaxinc.kcipt.classloader

import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec
import java.net.URL
import java.util.*

class DelegateClassloaderTest : AnnotationSpec() {
    @Test
    fun `loadClass(String) - delegate has Class`() {
        val dcl = DelegateClassloader(DummyParent(), DummyDelegate())
        dcl.loadClass("have") shouldBe DummyDelegateClass::class.java
    }

    @Test
    fun `loadClass(String) - delegate does not have Class`() {
        val dcl = DelegateClassloader(DummyParent(), DummyDelegate())
        dcl.loadClass("doesn't have") shouldBe DummyParentClass::class.java
    }
}

internal class DummyDelegate : ClassLoader() {
    override fun loadClass(name: String?): Class<*> {
        if (name=="have") return DummyDelegateClass()::class.java
        else throw ClassNotFoundException(name)
    }

    override fun getResource(name: String?): URL? {
        return if (name=="have") URL("https://delegate.mock")
        else null
    }

    override fun getResources(name: String?): Enumeration<URL> {
        return if (name=="have") DummyDelegateEnumeration()
        else EmptyEnumeration()
    }
}

internal class DummyParent : ClassLoader() {
    override fun loadClass(name: String?): Class<*> = DummyParentClass()::class.java
    override fun getResource(name: String?): URL? = URL("https://parent.mock")
    override fun getResources(name: String?): Enumeration<URL> = DummyEnumeration()
}

internal class DummyDelegateClass
internal class DummyParentClass

internal class EmptyEnumeration : Enumeration<URL> {
    override fun hasMoreElements(): Boolean {
        return false
    }

    override fun nextElement(): URL {
        throw NoSuchElementException()
    }
}

internal class DummyDelegateEnumeration : Enumeration<URL> {
    override fun hasMoreElements(): Boolean {
        return true
    }

    override fun nextElement(): URL {
        return URL("https://delegate.unicorn")
    }
}

internal class DummyParentEnumeration : Enumeration<URL> {
    override fun hasMoreElements(): Boolean {
        return true
    }

    override fun nextElement(): URL {
        return URL("https://parent.unicorn")
    }
}
