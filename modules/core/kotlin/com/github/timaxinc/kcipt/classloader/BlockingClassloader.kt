package com.github.timaxinc.kcipt.classloader

class BlockingClassloader(private val parentLoader: ClassLoader, private val blocked: MutableList<String>) : ClassLoader() {

    constructor(parentLoader: ClassLoader, vararg blocked: String) : this(parentLoader, blocked.toMutableList())

    fun add(toBlock: String) = blocked.add(toBlock)
    fun remove(toRemove: String) = blocked.remove(toRemove)

    override fun loadClass(name: String?): Class<*> {
        if (name==null) {
            return parentLoader.loadClass(name)
        }


        when(val it = name startsWithMember blocked) {
            is Block.None-> {
                return parentLoader.loadClass(name)
            }
            is Block.Class -> throw ClassBlockedException(it.className)
            is Block.Package -> throw PackageBlockedException(it.packageName)
        }
    }

    /**
     * Checks uf there if the passed element starts with any of the members of the MutableList.
     *
     * @param list the List with the {@link String Strings} which shall be used for the check
     *
     * @return {@code true} if the String starts with at least one of the members of the members of the List; {@code
     *         false} otherwise
     */
    private infix fun String.startsWithMember(list: List<String>): Block {
        list.forEach {
            if (this.startsWith(it)) {
                if (this == it) return Block.Class(it)
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

    abstract class BlockedException internal constructor(message: String) : Exception(message)
    class ClassBlockedException internal constructor(name: String) : BlockedException("Class $name is blocked")
    class PackageBlockedException internal constructor(name: String) : BlockedException("Package $name is blocked")
}
