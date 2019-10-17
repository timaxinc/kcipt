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
 * @property softMode
 *          If activated, softMode will prevent an Exception to be thrown, if the Class is on the blacklist
 * @property blacklist
 *          the blacklist containing names of Classes, resources, as well as of packages, which may not be used when
 *          loading a class or resource.
 *
 * @constructor
 *          Instantiates a new BlacklistClassloader.
 *
 * @param parent
 *          the parent ClassLoader
 */
class BlacklistClassloader(softMode: Boolean, private val blacklist: List<String>, parent: ClassLoader? = null) :
        BlockingClassloader(softMode, parent) {

    /**
     * Creates a BlockingClassloader with the specified parent and a blacklist containing the passed elements.
     *
     * @param softMode
     *          If activated, softMode will prevent an Exception to be thrown, if the Class is on the blacklist
     * @param parent
     *          the parent ClassLoader
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
        if (name == null) {
            return getResource(name)
        }

        return if (softMode) {
            when (name startsWithMember blacklist) {
                is Block.None -> super.getResource(name)
                else          -> null
            }
        } else {
            when (val it = name startsWithMember blacklist) {
                is Block.None    -> super.getResource(name)
                is Block.Exact   -> throw ResourceBlockedException(name)
                is Block.Package -> throw PackageBlockedException(it.packageName)
            }
        }
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
        if (name == null) {
            return getResources(name)
        }

        return if (softMode) {
            when (name startsWithMember blacklist) {
                is Block.None -> super.getResources(name)
                else          -> null
            }
        } else {
            when (val it = name startsWithMember blacklist) {
                is Block.None    -> super.getResources(name)
                is Block.Exact   -> throw ResourceBlockedException(name)
                is Block.Package -> throw PackageBlockedException(it.packageName)
            }
        }
    }
}
