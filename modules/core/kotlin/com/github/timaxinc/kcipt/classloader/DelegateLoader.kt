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
     * to load the Class, loadClass will use the fallback parent ClassLoader.
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

    /**
     * GetResource loads the resource with the given name from the delegate ClassLoader. If the delegate loader is
     * unable to load the resource, getResource will use the fallback parent ClassLoader.
     *
     * @param name
     *          the name of the resource
     * @return
     *          URL object for reading the resource; null if the resource could not be found, a URL could not be
     *          constructed to locate the resource, the resource is in a package that is not opened unconditionally, or
     *          access to the resource is denied by the security manager.
     */
    override fun getResource(name: String?): URL? {
        return Objects.requireNonNullElseGet(delegate.getResource(name)) {
            parent.getResource(name)
        }
    }

    /**
     * GetResources loads all resources associated with the given name from the delegate ClassLoader. If the delegate
     * loader is unable to load the resource, getResources will use the fallback parent ClassLoader.
     *
     * @param name
     *          the name of the resources to load
     * @return
     *          An enumeration of URL objects for the resource. If no resources could be found, the enumeration will be
     *          empty. Resources for which a URL cannot be constructed, are in package that is not opened
     *          unconditionally, or access to the resource is denied by the security manager, are not returned in the
     *          enumeration.
     */
    override fun getResources(name: String?): Enumeration<URL> {
        val delegateResources = delegate.getResources(name)

        return if (delegateResources.hasMoreElements()) {
            delegateResources
        } else {
            parent.getResources(name)
        }
    }
}
