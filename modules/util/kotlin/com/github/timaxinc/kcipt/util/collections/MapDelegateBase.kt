package com.github.timaxinc.kcipt.util.collections

import com.github.timaxinc.kcipt.util.factory.Factory

//TODO doc
open class MapDelegateBase<K, V, T : V>(
        private val storeDefault: Boolean = true, private val defaultValueFactory: Factory<T>? = null
) {

    //TODO doc
    protected fun getValueProtected(key: K, thisRef: Map<K, V>): T {
        val valueInConfiguration = thisRef[key]
        return run {
            if (valueInConfiguration != null) {
                try {
                    valueInConfiguration as T
                } catch (t: Throwable) {
                    storeAndGetDefault(thisRef, key)
                }
            } else storeAndGetDefault(thisRef, key)
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

    //TODO doc
    protected fun setValueProtected(key: K, thisRef: MutableMap<K, V>, value: T) {
        @Suppress("UNCHECKED_CAST") (thisRef as MutableMap<Any?, Any?>)[key] = value
    }

    /**
     * is thrown if no value is present in [Map]
     *
     * @constructor
     * @param key the key in [Map]
     */
    class NoValueFoundException(key: String) : Exception("no value mapped to $key")
}