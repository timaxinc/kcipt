package com.github.timaxinc.kcipt.classloader

import java.net.URL
import java.util.*

class StackClassloader(private val loaders: MutableList<ClassLoader>) : ClassLoader() {
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

    override fun getResource(name: String?): URL? {
        loaders.forEach {
            val resource = it.getResource(name)
            if (resource != null) return resource
        }
        return null
    }

    override fun getResources(name: String?): Enumeration<URL> {
        val resources = mutableListOf<Enumeration<URL>>()

        loaders.forEach {
            val cLResources = it.getResources(name)
            if (cLResources.hasMoreElements()) {
                resources.add(cLResources)
            }
        }

        return URLEnumeration(resources)
    }
}