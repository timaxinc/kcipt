package com.github.timaxinc.kcipt.classloader

import java.net.URL
import java.util.*

abstract class BlockingClassloader(protected val softMode: Boolean, parent: ClassLoader?) : ClassLoader(parent) {

    protected sealed class Block {
        object None : Block()
        class Single(val name: String) : Block()
        class Package(val name: String) : Block()
    }

    /**
     * Checks if a Class/resource, a package or nothing should be blocked.
     *
     * @param name
     *          the name of the resource/Class to check
     * @return
     *          the Block type defining what is blocked
     */
    protected abstract fun blockCheck(name: String?): Block

    /**
     * LoadClass loads the class with the specified name. Depending on what blockCheck returns and if softMode is
     * enabled, it will take the necessary actions.
     *
     * @param name
     *          the name of the class to be loaded
     * @return
     *          the loaded class, null if softMode is on and the Class is blocked
     *
     * @throws BlockingClassloader.ClassBlockedException
     *          if blockCheck returns Block.Single and softMode is off
     * @throws BlockingClassloader.PackageBlockedException
     *          if blockCheck returns Block.Package and softMode is off
     */
    override fun loadClass(name: String?): Class<*>? {
        return if (softMode) {
            when (blockCheck(name)) {
                is Block.None -> super.loadClass(name)
                else          -> null
            }
        } else {
            when (val it = blockCheck(name)) {
                is Block.None    -> super.loadClass(name)
                is Block.Single  -> throw ClassBlockedException(it.name)
                is Block.Package -> throw PackageBlockedException(it.name)
            }
        }
    }

    /**
     * GetResource will get the first resource matched by the passed name. Depending on what blockCheck returns and if softMode is
     * enabled, it will take the necessary actions.
     *
     * @param name
     *          the name of the class whose resource is to be returned
     * @return
     *          the resource, null if softMode is on and the resource is blocked
     *
     * @throws BlockingClassloader.ResourceBlockedException
     *          if blockCheck returns Block.Single and softMode is off
     * @throws BlockingClassloader.PackageBlockedException
     *          if blockCheck returns Block.Package and softMode is off
     */
    override fun getResource(name: String?): URL? {
        return if (softMode) {
            when (blockCheck(name)) {
                is Block.None -> super.getResource(name)
                else          -> null
            }
        } else {
            when (val it = blockCheck(name)) {
                is Block.None    -> super.getResource(name)
                is Block.Single  -> throw ResourceBlockedException(it.name)
                is Block.Package -> throw PackageBlockedException(it.name)
            }
        }
    }

    /**
     * GetResources gets all resources matching the passed name. Depending on what blockCheck returns and if softMode is
     * enabled, it will take the necessary actions.
     *
     * @param name
     *          the name of the class whose resources are to be returned
     * @return
     *          the resources, null if softMode is on and the resources are blocked
     *
     * @throws BlockingClassloader.ResourceBlockedException
     *          if blockCheck returns Block.Single and softMode is off
     * @throws BlockingClassloader.PackageBlockedException
     *          if blockCheck returns Block.Package and softMode is off
     */
    override fun getResources(name: String?): Enumeration<URL>? {
        return if (softMode) {
            when (blockCheck(name)) {
                is Block.None -> super.getResources(name)
                else          -> null
            }
        } else {
            when (val it = blockCheck(name)) {
                is Block.None    -> super.getResources(name)
                is Block.Single  -> throw ResourceBlockedException(it.name)
                is Block.Package -> throw PackageBlockedException(it.name)
            }
        }
    }

    /**
     * Checks if there if the passed element starts with any of the members of the MutableList.
     *
     * @param list
     *          the List with the String which shall be used for the check
     *
     * @return
     *          {@code true} if the String starts with at least one of the members of the members of the List; {@code
     *          false} otherwise
     */
    protected infix fun String.startsWithMember(list: List<String>): StartsWith {
        list.forEach {
            if (this.startsWith(it)) {
                if (this == it) return StartsWith.Exact
                return StartsWith.Package(it)
            }
        }
        return StartsWith.None
    }

    protected sealed class StartsWith {
        object None : StartsWith()
        object Exact : StartsWith()
        class Package(val packageName: String) : StartsWith()
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
     * ResourceBlockedException gets thrown in case the specified resource is present on the blacklist.
     *
     * @constructor
     *          Instantiates a new ResourceBlockedException.
     *
     * @param name
     *          The name of the Resource being blocked.
     */
    class ResourceBlockedException internal constructor(name: String) : BlockedException("Resource $name is blocked")

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