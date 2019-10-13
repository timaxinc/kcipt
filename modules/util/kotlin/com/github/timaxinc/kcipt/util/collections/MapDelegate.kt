package com.github.timaxinc.kcipt.util.collections

import com.github.timaxinc.kcipt.util.factory.Factory
import com.github.timaxinc.kcipt.util.factory.factory
import kotlin.reflect.KProperty

/**
 * creates a [MapDelegate]
 *
 * @param K type of [MapDelegate] key
 * @param V type of [MapDelegate] value
 * @param T type of object that is stored with delegate
 * @param defaultValueFactoryBlock lambda that creates the default value of the created [MapDelegate]
 * @return the created [MapDelegate]
 */
fun <K, V, T : V> delegate(storeDefault: Boolean = true, defaultValueFactoryBlock: () -> T): MapDelegate<K, V, T> =
        MapDelegate(factory(defaultValueFactoryBlock), storeDefault)

/**
 * creates a [MapDelegate]
 *
 * @param K type of [MapDelegate] key
 * @param V type of [MapDelegate] value
 * @param T type of object that is stored with delegate
 * @param defaultValueFactory [Factory] that creates the default value of the created [MapDelegate]
 * @return the created [MapDelegate]
 */
fun <K, V, T : V> delegate(
        storeDefault: Boolean = true,
        defaultValueFactory: Factory<T>? = null
): MapDelegate<K, V, T> =
        MapDelegate(defaultValueFactory, storeDefault)

/**
 * used to delegate a property to a [Map]
 *
 * @param K type of map key
 * @param V type of map value
 * @param T type of object that is stored with delegate
 * @property defaultValueFactory [Factory] that creates the default value if it isn't prepend in the [Map]
 * @property storeDefault defines if the default value should be stored in the [Map] if not prepend in [Map]
 * @property key the key in [Map] if null it tris to use property's name
 */
class MapDelegate<K, V, T : V>(
        private val defaultValueFactory: Factory<T>? = null,
        private val storeDefault: Boolean = true, private val key: K? = null
) {

    /**
     * operator function used to delegate properties
     *
     * @param thisRef the [Map] to store in
     * @param property the delegated [KProperty]
     * @return the value stored in [Map] or a new default value created by [defaultValueFactory]
     * @throws [NoValueFoundException]
     */
    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Map<K, V>, property: KProperty<*>): T {
        val key = key ?: checkIfPropertyNameIsSuitableKey(property)
        val valueInConfiguration = thisRef[key]
        return run {
            if (valueInConfiguration != null) {
                try {
                    valueInConfiguration as T
                } catch (classCastException: ClassCastException) {
                    storeAndGetDefault(thisRef, (key as K?)!!)
                }
            } else storeAndGetDefault(thisRef, (key as K?)!!)
        }.apply { if (this == null) throw NoValueFoundException(key.toString()) }!!
    }

    /**
     * if [defaultValueFactory] is not null and [storeDefault] is true it stores the created default value in the [map]
     *
     * @param map the delegate [Map]
     * @param key the key in [Map]
     * @return a new default value created by [defaultValueFactory]
     */
    private fun storeAndGetDefault(map: Map<K, V>, key: K): T? {
        return if (defaultValueFactory != null) {
            val defaultValue: T? = defaultValueFactory.invoke()
            if (storeDefault && defaultValue != null && map is MutableMap<K, V>) {
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
    operator fun setValue(thisRef: MutableMap<K, V>, property: KProperty<*>, value: T) {
        @Suppress("UNCHECKED_CAST") (thisRef as MutableMap<Any?, Any?>)[key ?: checkIfPropertyNameIsSuitableKey(property)] = value
    }

    /**
     * checks if property name is a suitable key else it throws a [NoKeySpecifiedException]
     *
     * @param property
     * @return
     * @throws NoKeySpecifiedException
     */
    private fun checkIfPropertyNameIsSuitableKey(property: KProperty<*>): T {
        try {
            @Suppress("UNCHECKED_CAST") return property.name as T
        } catch (classCastException: ClassCastException) {
            throw NoKeySpecifiedException()
        }
    }

    /**
     * is thrown if no value is present in [Map]
     *
     * @constructor
     * @param key the key in [Map]
     */
    class NoValueFoundException(key: String) : Exception("no value mapped to $key")

    /**
     * is thrown if no [key] is specified and property can't be used as the key
     */
    class NoKeySpecifiedException : Exception("you have to specific a key if map is not of type Map<String, *>")
}
