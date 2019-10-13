package com.github.timaxinc.kcipt.source

import com.github.timaxinc.kcipt.source.provider.FileSource
import com.google.common.jimfs.Configuration
import com.google.common.jimfs.Jimfs
import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec
import java.nio.file.Files

internal class FileSourceTest : AnnotationSpec() {

    private val virtualFileSystem = Jimfs.newFileSystem(Configuration.unix())

    @AfterClass
    fun closeVirtualFileSystem() {
        virtualFileSystem.close()
    }

    @Test
    fun `TEST - read content of file virtual`() {
        val path = virtualFileSystem.getPath("unicorn.txt")
        Files.write(path, "unicorn".toByteArray())

        val fileSource = FileSource(path)
        fileSource.read() shouldBe "unicorn"
    }

    @Test
    fun `FUNCTION - read write content of virtual file`() {
        val path = virtualFileSystem.getPath("unicorn.txt")
        val fileSource = FileSource(path)
        fileSource.write("mutable unicorn")
        fileSource.read() shouldBe "mutable unicorn"
    }
}
