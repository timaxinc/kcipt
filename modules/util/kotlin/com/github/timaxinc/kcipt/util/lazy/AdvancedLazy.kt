package com.github.timaxinc.kcipt.util.lazy

import kotlin.reflect.KProperty

class AdvancedLazy<T>(initializer: (KProperty<*>?) -> T, lock: Any? = null) : Lazy<T> {

    private var initializer: ((KProperty<*>?) -> T)? = initializer
    @Volatile
    private var _value: Any? = Unit
    private val lock = lock ?: this

    override val value: T
        get() = getValue(null, null)

    operator fun getValue(thisRef: T?, property: KProperty<*>?): T {
        val _v1 = _value
        if (_v1 !== Unit) {
            @Suppress("UNCHECKED_CAST") return _v1 as T
        }

        return synchronized(lock) {
            val _v2 = _value
            if (_v2 !== Unit) {
                @Suppress("UNCHECKED_CAST") (_v2 as T)
            } else {
                val typedValue = initializer!!(property)
                _value = typedValue
                initializer = null
                typedValue
            }
        }
    }

    override fun isInitialized(): Boolean = _value !== Unit

    override fun toString(): String = if (isInitialized()) value.toString() else "Lazy value not initialized yet."
}
