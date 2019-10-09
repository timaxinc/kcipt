package com.github.timaxinc.kcipt.classloader

import java.net.URL
import java.util.*

/**
 * BlockingClassLoader is a ClassLoader holding blacklist of packages and classes, as well as a parent ClassLoader.
 * All classes and packages contained in that List may not be used when attempting to load a Class or Resource
 *
 * @param parent
 *          the ClassLoader that will be used, if the requested class passes the blacklist check
 * @property blacklist
 *          the blacklist containing names of Classes, as well as of packages, which may not be used when loading a
 *          class or resource.
 */
class BlockingClassloader(parent: ClassLoader, private val blacklist: List<String>) :
        ClassLoader(parent) {

    constructor(parent: ClassLoader, vararg blocked: String) : this(parent, blocked.toList())

    /**
     * LoadClass loads the class with the specified name. If the class or its package is present in the blacklist,
     * loadClass will throw an Exception
     *
     * @param name
     *          the name of the class to be loaded
     * @return
     *          the loaded class
     *
     * @throws ClassBlockedException
     *          In case the requested class is blocked.
     * @throws PackageBlockedException
     *          in case the package containing the requested class is blocked.
     */
    override fun loadClass(name: String?): Class<*> {
        blockCheck(name)
        return super.loadClass(name)
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
     * @throws ClassBlockedException
     *          In case the requested class is blocked.
     * @throws PackageBlockedException
     *          in case the package containing the requested class is blocked.
     */
    override fun getResource(name: String?): URL? {
        blockCheck(name)
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
     * @throws ClassBlockedException
     *          In case the requested class is blocked.
     * @throws PackageBlockedException
     *          in case the package containing the requested class is blocked.
     */
    override fun getResources(name: String?): Enumeration<URL> {
        blockCheck(name)
        return super.getResources(name)
    }

    /**
     * Checks if the name of the class is on the blacklist of classes and packages. If that is the case, the
     * corresponding exception will be thrown.
     *
     * @param name
     *          the name of the class
     */
    private fun blockCheck(name: String?) {
        if (name==null) {
            return
        }

        when (val it = name startsWithMember blacklist) {
            is Block.None -> return
            is Block.Class -> throw ClassBlockedException(it.className)
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
                if (this==it) return Block.Class(it)
                return Block.Package(it)
            }
        }
        return Block.None
    }

    private sealed class Block {
        object None : Block()
        class Class(val className: String) : Block()
        class Package(val packageName: String) : Block()
    }

    /**
     * Abstract representation of an exception thrown, if the blacklist has prevented the requested operation.
     *
     * @constructor
     *          Instantiates a new BlockedException.
     *
     * @param message
     *          The message of the exception
     */
    abstract class BlockedException internal constructor(message: String) : Exception(message)

    /**
     * ClassBlockedException gets thrown in case the specified class is present on the blacklist.
     *
     * @constructor
     *          Instantiates a new ClassBlockedException.
     *
     * @param name
     *          The name of the Class being blocked.
     */
    class ClassBlockedException internal constructor(name: String) : BlockedException("Class $name is blocked")
    /**
     * PackageBlockedException gets thrown in case the package containing the class is present on the blacklist.
     *
     * @constructor
     *          Instantiates a new PackageBlockedException.
     *
     * @param name
     *          The name of the package causing the class to be blocked.
     */
    class PackageBlockedException internal constructor(name: String) : BlockedException("Package $name is blocked")
}
