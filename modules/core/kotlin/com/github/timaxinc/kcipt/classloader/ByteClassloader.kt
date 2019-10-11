package com.github.timaxinc.kcipt.classloader

/**
 * ByteClassloader is a ClassLoader holding a map with Classes in their byte form. Whenever loading a class, the
 * ByteClassLoader will search for the Classes byte code in the map. If there is no ByteArray with the specified name,
 * the parent ClassLoader will be used
 *
 * @property classDefs
 *          The various Classes in their byte code form. The key represents the qualified name of the Class, and the
 *          Value it's respective bte code
 * @constructor
 *          Instantiates a new ByteClassloader with the specified classDefs and the specified parent.
 *
 * @param parent
 *          The parent ClassLoader of the ByteClassloader
 */
class ByteClassloader(parent: ClassLoader, private val classDefs: Map<String, ByteArray>) : ClassLoader(parent) {

    /**
     * Finds the Class with the specified name and returns it.
     *
     * @param name
     *          the name of the Class to be loaded
     * @return
     *          the loaded Class
     */
    override fun findClass(name: String?): Class<*> {
        if (classDefs.containsKey(name)) {
            val classBytes = classDefs[name]
            if (classBytes != null) {
                return defineClass(name, classBytes, 0, classBytes.size)
            }
        }
        return super.findClass(name)
    }
}
