package com.github.timaxinc.kcipt.classloader

class DelegateClassloader(private val loaders: MutableList<ClassLoader>) : ClassLoader() {
    constructor(vararg loader: ClassLoader) : this(loader.toMutableList())

    fun add(loader: ClassLoader) = loaders.add(loader)
    fun remove(loader: ClassLoader) = loaders.remove(loader)

    override fun loadClass(name: String?): Class<*> {
        loaders.forEach {
            try {
                return it.loadClass(name)
            } catch (e: ClassNotFoundException) {
                // Do nothing, as another classloader might have the class
            }
        }
        throw ClassNotFoundException(name)
    }
}