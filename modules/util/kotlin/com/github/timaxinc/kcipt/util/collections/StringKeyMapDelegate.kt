package com.github.timaxinc.kcipt.util.collections

import com.github.timaxinc.kcipt.util.factory.Factory
import com.github.timaxinc.kcipt.util.factory.factory
import kotlin.reflect.KProperty

//TODO check doc
/**
 * creates a [StringKeyMapDelegate]
 *
 * @param V type of [StringKeyMapDelegate] value
 * @param T type of object that is stored with delegate
 * @param storeDefault defines if the default value should be stored in the [Map] if not prepend in [Map]
 * @param defaultValueFactoryBlock lambda that creates the default value of the created [StringKeyMapDelegate]
 * @return the created [StringKeyMapDelegate]
 */
fun <V, T : V> delegate(
        storeDefault: Boolean = true, defaultValueFactoryBlock: () -> T
): StringKeyMapDelegate<V, T> = StringKeyMapDelegate<V, T>(storeDefault, factory(defaultValueFactoryBlock))

//TODO check doc
/**
 * creates a [StringKeyMapDelegate]
 *
 * @param V type of [StringKeyMapDelegate] value
 * @param T type of object that is stored with delegate
 * @param storeDefault defines if the default value should be stored in the [Map] if not prepend in [Map]
 * @param defaultValueFactory [Factory] that creates the default value if it isn't prepend in the [Map]
 * @return the created [StringKeyMapDelegate]
 */
fun <V, T : V> delegate(
        storeDefault: Boolean = true, defaultValueFactory: Factory<T>? = null
): StringKeyMapDelegate<V, T> = StringKeyMapDelegate<V, T>(storeDefault, defaultValueFactory)

//TODO check doc
/**
 * used to delegate a property to a [Map]
 *
 * @param V type of map value
 * @param T type of object that is stored with delegate
 * @property defaultValueFactory [Factory] that creates the default value if it isn't prepend in the [Map]
 * @property storeDefault defines if the default value should be stored in the [Map] if not prepend in [Map]
 */
class StringKeyMapDelegate<V, T : V>(
        storeDefault: Boolean = true, defaultValueFactory: Factory<T>? = null
) : MapDelegateBase<String, V, T>() {

    //TODO check doc
    /**
     * operator function used to delegate properties
     *
     * @param thisRef the [Map] to store in
     * @param property the delegated [KProperty]
     * @return the value stored in [Map] or a new default value created by [defaultValueFactory]
     * @throws [NoValueFoundException]
     */
    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Map<String, V>, property: KProperty<*>): T {
        val key = property.name
        return getValueProtected(key, thisRef)
    }

    //TODO check doc
    /**
     * operator function used to delegate properties
     *
     * @param thisRef the [Map] to store in
     * @param property the delegated [KProperty]
     * @param value
     */
    operator fun setValue(thisRef: MutableMap<String, V>, property: KProperty<*>, value: T) {
        val key = property.name
        setValueProtected(key, thisRef, value)
    }
}
