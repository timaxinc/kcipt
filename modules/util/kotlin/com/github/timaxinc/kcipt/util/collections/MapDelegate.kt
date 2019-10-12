package com.github.timaxinc.kcipt.util.collections

import com.github.timaxinc.kcipt.util.factory.Factory
import com.github.timaxinc.kcipt.util.factory.factory
import kotlin.reflect.KProperty

/**
 * creates a [MutableMapDelegate]
 *
 * @param V type of [MutableMapDelegate] value
 * @param defaultValueFactoryBlock lambda that creates the default value of the created [MutableMapDelegate]
 * @return the created [MutableMapDelegate]
 */
fun <V : Any> delegate(defaultValueFactoryBlock: () -> V): MutableMapDelegate<V> = MutableMapDelegate(factory(defaultValueFactoryBlock))

/**
 * creates a [MutableMapDelegate]
 *
 * @param V type of [MutableMapDelegate] value
 * @param defaultValueFactory [Factory] that creates the default value of the created [MutableMapDelegate]
 * @return the created [MutableMapDelegate]
 */
fun <V : Any> delegate(defaultValueFactory: Factory<V>? = null): MutableMapDelegate<V> = MutableMapDelegate(defaultValueFactory)

/**
 * used to delegate a property to a [Map] with key type [String]
 *
 * @param V the value type
 * @property defaultValueFactory [Factory] that creates the default value if it isn't prepend in the [Map]
 * @property storeDefault defines if the default value should be stored in the [Map] if not prepend in [Map]
 * @property key the key in [Map] if null it uses property's name
 */
class MutableMapDelegate<V : Any> internal constructor(
        private val defaultValueFactory: Factory<V>? = null, private val storeDefault: Boolean = true, private val key: String? = null
) {

    /**
     * operator function used to delegate properties
     *
     * @param thisRef the [Map] to store in
     * @param property the delegated [KProperty]
     * @return the value stored in [Map] or a new default value created by [defaultValueFactory]
     */
    operator fun getValue(thisRef: Map<String, Any>, property: KProperty<*>): V {
        val key = key ?: property.name
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

    /**
     * if [defaultValueFactory] is not null and [storeDefault] is true it stores the created default value in the [map]
     *
     * @param map the delegate [Map]
     * @param key the key in [Map]
     * @return a new default value created by [defaultValueFactory]
     */
    private fun storeAndGetDefault(map: Map<String, Any>, key: String): V? {
        return if (defaultValueFactory != null) {
            val defaultValue: V? = defaultValueFactory.invoke()
            if (storeDefault && defaultValue != null && map is MutableMap) {
                map[key] = defaultValue
            }
            defaultValue
        } else null
    }

    /**
     * operator function used to delegate properties
     *
     * @param thisRef the [Map] to store in
     * @param property the delegated [KProperty]
     * @param value
     */
    operator fun setValue(thisRef: MutableMap<String, Any>, property: KProperty<*>, value: V) {
        thisRef[key ?: property.name] = value
    }

    /**
     * is thrown if no value is present in [Map]
     *
     * @constructor
     * @param key the key in [Map]
     */
    class NoValueFoundException(key: String) : Exception("No value mapped to $key")
}
