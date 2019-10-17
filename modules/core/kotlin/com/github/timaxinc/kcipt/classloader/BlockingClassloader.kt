package com.github.timaxinc.kcipt.classloader

abstract class BlockingClassloader(protected val softMode: Boolean, parent: ClassLoader?) : ClassLoader(parent) {

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
    protected infix fun String.startsWithMember(list: List<String>): Block {
        list.forEach {
            if (this.startsWith(it)) {
                if (this == it) return Block.Exact
                return Block.Package(it)
            }
        }
        return Block.None
    }

    protected sealed class Block {
        object None : Block()
        object Exact : Block()
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