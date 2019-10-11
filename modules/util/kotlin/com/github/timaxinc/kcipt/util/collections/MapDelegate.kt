package com.github.timaxinc.kcipt.util.collections

import com.github.timaxinc.kcipt.util.factory.Factory
import com.github.timaxinc.kcipt.util.factory.factory
import kotlin.reflect.KProperty

fun <V : Any> delegate(defaultValueFactoryBlock: () -> V): MutableMapDelegate<V> =
        MutableMapDelegate(factory(defaultValueFactoryBlock))

fun <V : Any> delegate(defaultValueFactory: Factory<V>? = null): MutableMapDelegate<V> =
        MutableMapDelegate(defaultValueFactory)

class MutableMapDelegate<V : Any> internal constructor(
        private val defaultValueFactory: Factory<V>? = null,
        private val storeDefault: Boolean = true,
        private val key: String? = null
) {

    operator fun getValue(thisRef: Map<String, Any>, property: KProperty<*>): V {
        val key =
                key
                ?: property.name
        val valueInConfiguration = thisRef[key]
        return run {
            if (valueInConfiguration != null) {
                try {
                    @Suppress("UNCHECKED_CAST") valueInConfiguration as V
                } catch (classCastException: ClassCastException) {
                    storeAndGetDefault(thisRef, key)
                }
            } else storeAndGetDefault(thisRef, key)
        }.apply { if (this == null) throw NoValueFoundException(key) }!!
    }

    private fun storeAndGetDefault(map: Map<String, Any>, key: String): V? {
        return if (defaultValueFactory != null) {
            val defaultValue: V? = defaultValueFactory.invoke()
            if (storeDefault && defaultValue != null && map is MutableMap) {
                map[key] = defaultValue
            }
            defaultValue
        } else null
    }

    operator fun setValue(thisRef: MutableMap<String, Any>, property: KProperty<*>, value: V) {
        thisRef[key
                ?: property.name] = value
    }

    class NoValueFoundException(key: String) : Exception("No value mapped to $key")
}