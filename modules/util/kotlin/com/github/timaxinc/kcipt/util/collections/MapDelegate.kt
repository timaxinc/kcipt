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
 * @param key the key in [Map] if null it tris to use property's name
 * @param storeDefault defines if the default value should be stored in the [Map] if not prepend in [Map]
 * @param defaultValueFactoryBlock lambda that creates the default value of the created [MapDelegate]
 * @return the created [MapDelegate]
 */
fun <K, V, T : V> delegate(
        key: K, storeDefault: Boolean = true, defaultValueFactoryBlock: () -> T
): MapDelegate<K, V, T> = MapDelegate<K, V, T>(key, storeDefault, factory(defaultValueFactoryBlock))

/**
 * creates a [MapDelegate]
 *
 * @param K type of [MapDelegate] key
 * @param V type of [MapDelegate] value
 * @param T type of object that is stored with delegate
 * @param key the key in [Map] if null it tris to use property's name
 * @param storeDefault defines if the default value should be stored in the [Map] if not prepend in [Map]
 * @param defaultValueFactory [Factory] that creates the default value if it isn't prepend in the [Map]
 * @return the created [MapDelegate]
 */
fun <K, V, T : V> delegate(
        key: K, storeDefault: Boolean = true, defaultValueFactory: Factory<T>? = null
): MapDelegate<K, V, T> = MapDelegate<K, V, T>(key, storeDefault, defaultValueFactory)

/**
 * used to delegate a property to a [Map]
 *
 * @param K type of map key
 * @param V type of map value
 * @param T type of object that is stored with delegate
 * @property defaultValueFactory [Factory] that creates the default value if it isn't prepend in the [Map]
 * @property storeDefault defines if the default value should be stored in the [Map] if not prepend in [Map]
 * @property key the key in [Map]
 */
class MapDelegate<K, V, T : V>(
        private val key: K, storeDefault: Boolean = true, defaultValueFactory: Factory<T>? = null
) : MapDelegateBase<K, V, T>(storeDefault, defaultValueFactory) {

    /**
     * operator function used to delegate properties
     *
     * @param thisRef the [Map] to store in
     * @param property the delegated [KProperty]
     * @return the value stored in [Map] or a new default value created by [defaultValueFactory]
     * @throws [MapDelegateBase.NoValueFoundException]
     */
    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Map<K, V>, property: KProperty<*>): T {
        return getValueProtected(key, thisRef)
    }

    /**
     * operator function used to delegate properties
     *
     * @param thisRef the [Map] to store in
     * @param property the delegated [KProperty]
     * @param value
     */
    operator fun setValue(thisRef: MutableMap<K, V>, property: KProperty<*>, value: T) {
        setValueProtected(key, thisRef, value)
    }
}
