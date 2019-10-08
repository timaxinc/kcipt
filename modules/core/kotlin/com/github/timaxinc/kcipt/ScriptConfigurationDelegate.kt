package com.github.timaxinc.kcipt

import kotlin.reflect.KProperty

fun <T : Any> delegate(default: T): ConfigurationDelegate<T> = ConfigurationDelegate(default)

class ConfigurationDelegate<T : Any> internal constructor(
        private val defaultValue: T,
        private val key: String? = null
) {

    operator fun getValue(thisRef: ScriptConfiguration, property: KProperty<*>): T {
        val valueInConfiguration = thisRef[key ?: property.name]
        return if (valueInConfiguration != null) {
            try {
                @Suppress("UNCHECKED_CAST")
                valueInConfiguration as T
            } catch (classCastException: ClassCastException) {
                defaultValue
            }
        } else {
            defaultValue
        }
    }
    operator fun setValue(thisRef: ScriptConfiguration, property: KProperty<*>, value: T) {
        thisRef[key ?: property.name] = value
    }

}