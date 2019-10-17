package com.github.timaxinc.kcipt.classloader

import com.github.timaxinc.kcipt.util.io.Block
import com.github.timaxinc.kcipt.util.io.startsWithMember
import java.net.URL
import java.util.*

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

    /**
     * LoadClass loads the Class with the specified name, if the class is on the whitelist. If not and softMode is
     * turned on, null will be returned. If softMode is off an ClassBlockedException will be thrown.
     *
     * @throws BlockingClassloader.ClassBlockedException
     *          Thrown if softMode is off and the requested Class is not on the whitelist.
     *
     * @param name
     *          the name of the Class that is to be loaded
     * @return
     *          the requested Class or null if the requested Class is not on the whitelist and softMode is on
     */
    override fun loadClass(name: String?): Class<*>? {
        if (name == null) {
            return super.loadClass(name)
        }

        return if (softMode) {
            when (name startsWithMember whitelist) {
                Block.None -> null
                else       -> super.loadClass(name)
            }
        } else {
            when (name startsWithMember whitelist) {
                Block.None -> throw ClassBlockedException(name)
                else       -> super.loadClass(name)
            }
        }
    }

    /**
     * GetResource gets an url pointing to the specified resource, if the resource is on the whitelist. If not and
     * softMode is on, null will be returned. If softMode is off, a ResourceBlockedException will be thrown.
     *
     * @throws BlockingClassloader.ResourceBlockedException
     *          if the requested resource is not on the whitelist and softMode is off
     *
     * @param name
     *          the name of the requested resource
     * @return
     *          an URL pointing to the resources location. If the resource is not found null will be returned.
     *          Furthermore null will be returned if the requested resource is not on the whitelist and softMode is on.
     */
    override fun getResource(name: String?): URL? {
        return whitelistedResourceCheckElseGet(name) { super.getResource(name) }
    }

    /**
     * GetResources gets all resources associated with the passed name. If no resource is found null is returned.
     * Additionally if the resource is not on the whitelist and softMode is on, null will be returned. If softMode is
     * off a ResourceBlockedException will be thrown.
     *
     * @throws BlockingClassloader.ResourceBlockedException
     *              if the requested resource is not on the whitelist and softMode is off
     *
     * @param name
     *          the name of the requested resource
     * @return
     *          an Enumeration of URLs pointing to the locations of the resources. If the requested resources is not
     *          on the whitelist and softMode is on, null will be returned.
     */
    override fun getResources(name: String?): Enumeration<URL>? {
        return whitelistedResourceCheckElseGet(name) { super.getResources(name) }
    }

    /**
     * WhitelistedResourceCheckElseGet checks if the resource with the specified name is on the whitelist. If it is
     * the block function will be executed and returned. If not and softMode is on, null will be returned. If
     * softMode is off, a ResourceBlockedException will be thrown.
     *
     * @param T
     *          the type of the return type
     * @param name
     *          the name of the requested resource
     * @param block
     *          the function which's return value will be returned if the requested resource is on the whitelist
     * @return
     *          the return value of block. If the requested resource is not on the whitelist and softMode is on, null
     *          will be returned.
     */
    private inline fun <T> whitelistedResourceCheckElseGet(name: String?, block: () -> T): T? {
        if (name == null) {
            return block()
        }

        return if (softMode) {
            when (name startsWithMember whitelist) {
                is Block.None -> null
                else          -> block()
            }
        } else {
            when (name startsWithMember whitelist) {
                is Block.None -> throw ResourceBlockedException(name)
                else          -> block()
            }
        }
    }
}