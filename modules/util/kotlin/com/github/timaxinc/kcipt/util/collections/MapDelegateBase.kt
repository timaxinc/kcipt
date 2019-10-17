package com.github.timaxinc.kcipt.util.collections

import com.github.timaxinc.kcipt.util.factory.Factory

/**
 * implemented by delegates that use a [Map] as value store
 *
 * @param K type of map key
 * @param V type of map value
 * @param T type of object that is stored with delegate
 * @property defaultValueFactory [Factory] that creates the default value if it isn't prepend in the [Map]
 * @property storeDefault defines if the default value should be stored in the [Map] if not prepend in [Map]
 */
abstract class MapDelegateBase<K, V, T : V>(
        private val storeDefault: Boolean = true, private val defaultValueFactory: Factory<T>? = null
) {

    /**
     * reads the value with the key [key] from the [map]
     *
     * @param key the key of the requested value
     * @param map the [Map] to store in
     * @return the value stored in [Map] or a new default value created by [defaultValueFactory]
     * @throws [MapDelegateBase.NoValueFoundException]
     */
    protected fun getValueProtected(key: K, map: Map<K, V>): T {
        val valueInConfiguration = map[key]
        return run {
            if (valueInConfiguration != null) {
                try {
                    valueInConfiguration as T
                } catch (t: Throwable) {
                    storeAndGetDefault(map, key)
                }
            } else storeAndGetDefault(map, key)
        }.apply { if (this == null) throw NoValueFoundException(key.toString()) }!!
    }

    /**
     * if [defaultValueFactory] is not null and [storeDefault] is true it stores the created default value in the [map]
     *
     * @param map the delegate [Map]
     * @param key the key in [Map]
     * @return a new default value created by [defaultValueFactory]
     */
    protected fun storeAndGetDefault(map: Map<K, V>, key: K): T? {
        return if (defaultValueFactory != null) {
            val defaultValue: T? = defaultValueFactory.invoke()
            if (storeDefault && defaultValue != null && map is MutableMap<K, V>) {
                map[key] = defaultValue
            }
            defaultValue
        } else null
    }

    /**
     * stores the [value] with a key [key] into the [map]
     *
     * @param key the key to use
     * @param map the map to store in
     * @param value the value stat should be stored
     */
    protected fun setValueProtected(key: K, map: MutableMap<K, V>, value: T) {
        map[key] = value
    }

    /**
     * is thrown if no value is present in [Map]
     *
     * @constructor
     * @param key the key in [Map]
     */
    class NoValueFoundException(key: String) : Exception("no value mapped to $key")
}
