package com.github.timaxinc.kcipt.classloader

import java.net.URL
import java.util.*

/**
 * The delegateLoader is a ClassLoader holding a primary, delegate ClassLoader, and a fallback, parent ClassLoader. Upon
 * accessing a Class or resource, the DelegateClassloader will attempt to fetch the Class/ resource from the delegate.
 * If that does not succeed, it will use the fallback parent ClassLoader.
 * What makes this ClassLoader special, is the user having the ability to swap out the delegate loader any time.
 *
 * @property delegate
 *          the primary, delegate ClassLoader
 * @constructor
 *          Constructs a DelegateClassloader with the specified parent and delegate
 *
 * @param parent
 *          the fallback, parent ClassLoader
 */
class DelegateClassloader(parent: ClassLoader, val delegate: ClassLoader): ClassLoader(parent) {
    /**
     * LoadClass loads the Class with the specified name from the delegate ClassLoader. If the delegate loader is unable
     * to fetch the Class, loadClass will use the fallback parent ClassLoader.
     *
     * @param name
     *          the name of the Class
     * @return
     *          the fetched Class
     */
    override fun loadClass(name: String?): Class<*> {
        return try {
            delegate.loadClass(name)
        } catch (e: ClassNotFoundException) {
            parent.loadClass(name)
        }
    }
}