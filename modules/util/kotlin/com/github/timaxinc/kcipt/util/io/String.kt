package com.github.timaxinc.kcipt.util.io

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
infix fun String.startsWithMember(list: List<String>): Block {
    list.forEach {
        if (this.startsWith(it)) {
            if (this == it) return Block.Exact
            return Block.Package(it)
        }
    }
    return Block.None
}

sealed class Block {
    object None : Block()
    object Exact : Block()
    class Package(val packageName: String) : Block()
}