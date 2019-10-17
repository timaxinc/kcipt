package com.github.timaxinc.kcipt.classloader

/**
 * WhitelistClassloader is a ClassLoader with a predefined Whitelist of Classes and packages that it will allow to
 * load resources and Classes from. All other requests will be blocked
 *
 * @property softMode
 *          If activated, softMode will prevent an Exception to be thrown, if the Class is on the blacklist
 * @property whitelist
 *          A List of Strings containing the names of Classes packages and resources that are to be whitelisted. Only
 *          those Classes/resources will be permitted to load, while all other requests will be blocked.
 * @constructor
 *          Instantiates a new WhitelistClassloader.
 *
 * @param parent
 *          the parent ClassLoader
 */
class WhitelistClassloader(
        private val softMode: Boolean, private val whitelist: List<String>, parent: ClassLoader? = null
) : BlockingClassloader(parent) {

    /**
     * Creates a BlockingClassloader with the specified parent and a blacklist containing the passed elements.
     *
     * @param softMode
     *          If activated, softMode will prevent an Exception to be thrown, if the Class is on the blacklist
     * @param parent
     *           the parent ClassLoader
     * @param whitelist
     *          A List of Strings containing the names of Classes packages and resources that are to be whitelisted. Only
     *          those Classes/resources will be permitted to load, while all other requests will be blocked.
     */
    constructor(softMode: Boolean, vararg whitelist: String, parent: ClassLoader? = null) : this(
            softMode, whitelist.toList(), parent
    )
}