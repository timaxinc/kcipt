package com.github.timaxinc.kcipt.classloader

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

    override fun blockCheck(name: String?): Block {
        return if (name == null) {
            Block.None
        } else {
            when (val it = name startsWithMember blacklist) {
                is StartsWith.None    -> Block.None
                is StartsWith.Exact   -> Block.Single(name)
                is StartsWith.Package -> Block.Package(it.packageName)
            }
        }
    }
}
