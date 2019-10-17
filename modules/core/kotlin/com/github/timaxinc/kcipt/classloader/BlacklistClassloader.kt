package com.github.timaxinc.kcipt.classloader

import java.net.URL
import java.util.*

/**
 * BlockingClassLoader is a ClassLoader holding blacklist of packages and classes, as well as a parent ClassLoader.
 * All classes, resources and packages contained in that List may not be used when attempting to load a Class or
 * Resource.
 *
 * @param parent
 *          the ClassLoader that will be used, if the requested class passes the blacklist check
 * @property blacklist
 *          the blacklist containing names of Classes, resources, as well as of packages, which may not be used when
 *          loading a class or resource.
 */
class BlacklistClassloader(private val blacklist: List<String>, parent: ClassLoader? = null) : BlockingClassloader(
        parent
) {

    /**
     * Creates a BlockingClassloader with the specified parent and a blacklist containing the passed elements.
     *
     * @param parent
     *          the ClassLoader that will be used, if the requested class passes the blacklist check
     * @param blacklist
     *          the blacklist containing names of Classes, resources, as well as of packages, which may not be used when
     *          loading a class or resource.
     */
    constructor(vararg blacklist: String, parent: ClassLoader? = null) : this(blacklist.toList(), parent)

    /**
     * LoadClass loads the class with the specified name. If the class or its package is present in the blacklist,
     * loadClass will throw an Exception
     *
     * @param name
     *          the name of the class to be loaded
     * @return
     *          the loaded class
     *
     * @throws BlockingClassloader.ClassBlockedException
     *          In case the requested class is blocked.
     * @throws BlockingClassloader.PackageBlockedException
     *          in case the package containing the requested class is blocked.
     */
    override fun loadClass(name: String?): Class<*> {
        if (name == null) {
            return super.loadClass(name)
        }

        when (val it = name startsWithMember blacklist) {
            is Block.None    -> return super.loadClass(name)
            is Block.Exact   -> throw ClassBlockedException(name)
            is Block.Package -> throw PackageBlockedException(it.packageName)
        }
    }

    /**
     * GetResource will get the first resource matched by the passed name. If the class or its package is present in the
     * blacklist, loadClass will throw an Exception.
     *
     * @param name
     *          the name of the class whose resource is to be returned
     * @return
     *          the resource
     *
     * @throws BlockingClassloader.ResourceBlockedException
     *          In case the requested class is blocked.
     * @throws BlockingClassloader.PackageBlockedException
     *          in case the package containing the requested class is blocked.
     */
    override fun getResource(name: String?): URL? {
        blockResourceCheck(name)
        return super.getResource(name)
    }

    /**
     * GetResources gets all resources matching the passed name. f the class or its package is present in the blacklist,
     * loadClass will throw an Exception.
     *
     * @param name
     *          the name of the class whose resources are to be returned
     * @return
     *          the resources
     *
     * @throws BlockingClassloader.ResourceBlockedException
     *          In case the requested class is blocked.
     * @throws BlockingClassloader.PackageBlockedException
     *          in case the package containing the requested class is blocked.
     */
    override fun getResources(name: String?): Enumeration<URL> {
        blockResourceCheck(name)
        return super.getResources(name)
    }

    /**
     * Checks if the name of the resource is on the blacklist of resources and packages. If that is the case, the
     * corresponding exception will be thrown.
     *
     * @param name
     *          the name of the class
     */
    private fun blockResourceCheck(name: String?) {
        if (name == null) {
            return
        }

        when (val it = name startsWithMember blacklist) {
            is Block.None    -> return
            is Block.Exact   -> throw ResourceBlockedException(name)
            is Block.Package -> throw PackageBlockedException(it.packageName)
        }
    }

    /**
     * Checks uf there if the passed element starts with any of the members of the MutableList.
     *
     * @param list
     *          the List with the String which shall be used for the check
     *
     * @return
     *          {@code true} if the String starts with at least one of the members of the members of the List; {@code
     *          false} otherwise
     */
    private infix fun String.startsWithMember(list: List<String>): Block {
        list.forEach {
            if (this.startsWith(it)) {
                if (this == it) return Block.Exact
                return Block.Package(it)
            }
        }
        return Block.None
    }

    private sealed class Block {
        object None : Block()
        object Exact : Block()
        class Package(val packageName: String) : Block()
    }
}
