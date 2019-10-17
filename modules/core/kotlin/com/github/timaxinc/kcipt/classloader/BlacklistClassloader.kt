package com.github.timaxinc.kcipt.classloader

import com.github.timaxinc.kcipt.util.io.Block
import com.github.timaxinc.kcipt.util.io.startsWithMember
import java.net.URL
import java.util.*

/**
 * BlockingClassLoader is a ClassLoader holding blacklist of packages and classes, as well as a parent ClassLoader.
 * All classes, resources and packages contained in that List may not be used when attempting to load a Class or
 * Resource.
 *
 * @param softMode
 *          If activated, softMode will prevent an Exception to be thrown, if the Class is on the blacklist
 * @param parent
 *          the ClassLoader that will be used, if the requested class passes the blacklist check
 * @property blacklist
 *          the blacklist containing names of Classes, resources, as well as of packages, which may not be used when
 *          loading a class or resource.
 */
class BlacklistClassloader(
        private val softMode: Boolean, private val blacklist: List<String>, parent: ClassLoader? = null
) : BlockingClassloader(
        parent
) {

    /**
     * Creates a BlockingClassloader with the specified parent and a blacklist containing the passed elements.
     *
     * @param softMode
     *          If activated, softMode will prevent an Exception to be thrown, if the Class is on the blacklist
     * @param parent
     *          the ClassLoader that will be used, if the requested class passes the blacklist check
     * @param blacklist
     *          the blacklist containing names of Classes, resources, as well as of packages, which may not be used when
     *          loading a class or resource.
     */
    constructor(softMode: Boolean, vararg blacklist: String, parent: ClassLoader? = null) : this(
            softMode, blacklist.toList(), parent
    )

    /**
     * LoadClass loads the class with the specified name. If the class or its package is present in the blacklist,
     * loadClass will throw an Exception
     *
     * @param name
     *          the name of the class to be loaded
     * @return
     *          the loaded class, null if softMode is on and the Class is blocked
     *
     * @throws BlockingClassloader.ClassBlockedException
     *          In case the requested class is blocked and softMode is off
     * @throws BlockingClassloader.PackageBlockedException
     *          in case the package containing the requested class is blocked and softMode is off
     */
    override fun loadClass(name: String?): Class<*>? {
        if (name == null) {
            return super.loadClass(name)
        }

        return if (softMode) {
            when (name startsWithMember blacklist) {
                is Block.None -> super.loadClass(name)
                else          -> null
            }
        } else {
            when (val it = name startsWithMember blacklist) {
                is Block.None    -> super.loadClass(name)
                is Block.Exact   -> throw ClassBlockedException(name)
                is Block.Package -> throw PackageBlockedException(it.packageName)
            }
        }
    }

    /**
     * GetResource will get the first resource matched by the passed name. If the class or its package is present in the
     * blacklist, loadClass will throw an Exception.
     *
     * @param name
     *          the name of the class whose resource is to be returned
     * @return
     *          the resource, null if softMode is on and the resource is blocked
     *
     * @throws BlockingClassloader.ResourceBlockedException
     *          In case the requested class is blocked.
     * @throws BlockingClassloader.PackageBlockedException
     *          in case the package containing the requested class is blocked.
     */
    override fun getResource(name: String?): URL? {
        return blockedResourceCheckElseGet(name) { super.getResource(name) }
    }

    /**
     * GetResources gets all resources matching the passed name. f the class or its package is present in the blacklist,
     * loadClass will throw an Exception.
     *
     * @param name
     *          the name of the class whose resources are to be returned
     * @return
     *          the resources, null if softMode is on and the resources are blocked
     *
     * @throws BlockingClassloader.ResourceBlockedException
     *          In case the requested class is blocked and softMode is off
     * @throws BlockingClassloader.PackageBlockedException
     *          in case the package containing the requested class is blocked and softMode is off
     */
    override fun getResources(name: String?): Enumeration<URL>? {
        return blockedResourceCheckElseGet(name) { super.getResources(name) }
    }

    /**
     * Checks if the name of the resource is on the blacklist of resources and packages. If that is the case and
     * softMode is turned on, null will be returned. If softMode is off the respective Exception will be thrown. If
     * the requested resource passed the checks, the return value of the passed lambda will be returned.
     *
     * @param name
     *          the name of the class
     */
    private inline fun <T> blockedResourceCheckElseGet(name: String?, block: () -> T): T? {
        if (name == null) {
            return block()
        }

        return if (softMode) {
            when (name startsWithMember blacklist) {
                is Block.None -> block()
                else          -> null
            }
        } else {
            when (val it = name startsWithMember blacklist) {
                is Block.None    -> block()
                is Block.Exact   -> throw ResourceBlockedException(name)
                is Block.Package -> throw PackageBlockedException(it.packageName)
            }
        }
    }
}
