package com.github.timaxinc.kcipt.util.io

import kotlin.reflect.KClass

/**
 * wrapper used to work with resources more easily
 *
 * @constructor
 * @param path the path of the recourse
 * @param context the context [KClass] of the resource
 */
class Resource(path: String, context: KClass<*>? = null) {

    /**
     * the context [KClass] of the resource
     */
    val context = context ?: this::class

    /**
     * the path of the recourse
     */
    val path: String = (if (!path.startsWith("/")) "/" else "") + path

    /**
     * resource as URL
     */
    val url = this.context.java.getResource(this.path)

    /**
     * resource as URI
     */
    val uri = url?.toURI()

    /**
     * resource as external form
     */
    var externalForm = url?.toExternalForm()

    /**
     * resource as InputStream
     */
    var stream = this.context.java.getResourceAsStream(this.path)

    /**
     * reads the content of the Resource
     * @return content of Resource as [String]
     */
    fun readText() = url.readText()
}
