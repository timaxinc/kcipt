package com.github.timaxinc.kcipt.util.collections

import kotlin.reflect.KProperty

fun <V : Any> delegate(default: V): MutableMapDelegate<V> =
        MutableMapDelegate(default)

class MutableMapDelegate<V : Any> internal constructor(
        private val defaultValue: V,
        private val key: String? = null
) {

    operator fun getValue(thisRef: Map<String, Any>, property: KProperty<*>): V {
        val valueInConfiguration = thisRef[key ?: property.name]
        return if (valueInConfiguration != null) {
            try {
                @Suppress("UNCHECKED_CAST")
                valueInConfiguration as V
            } catch (classCastException: ClassCastException) {
                defaultValue
            }
        } else {
            defaultValue
        }
    }

    operator fun setValue(thisRef: MutableMap<String, Any>, property: KProperty<*>, value: V) {
        thisRef[key ?: property.name] = value
    }
}