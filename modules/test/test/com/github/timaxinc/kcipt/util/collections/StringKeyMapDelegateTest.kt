package com.github.timaxinc.kcipt.util.collections

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.AnnotationSpec

private var MutableMap<String, String>.delegated by delegate()
private var MutableMap<String, String>.delegatedWithDefault by delegate { "default" }
private var MutableMap<String, String>.delegatedWithDefaultButStoreOf by delegate(false) { "default" }

internal class StringKeyMapDelegateTest : AnnotationSpec() {

    @Test
    fun `TEST - get value from map`() {
        val map = mutableMapOf(MutableMap<String, String>::delegated.name to "value")

        map.delegated shouldBe "value"
    }

    @Test
    fun `TEST - get not defined value from map`() {
        val map = mutableMapOf<String, String>()

        shouldThrow<MapDelegateBase.NoValueFoundException> {
            map.delegated
        }
    }

    @Test
    fun `TEST - get value from default and store it in the map`() {
        val map = mutableMapOf<String, String>()

        map.delegatedWithDefault shouldBe "default"
    }

    @Test
    fun `TEST - get value from default and don't store it in the map`() {
        val map = mutableMapOf<String, String>()

        map.delegatedWithDefaultButStoreOf shouldBe "default"
        map.isEmpty() shouldBe true
    }

    @Test
    fun `TEST - set value`() {
        val map = mutableMapOf<String, String>()

        map.delegated = "value"
        map[MutableMap<String, String>::delegated.name] shouldBe "value"
    }
}
