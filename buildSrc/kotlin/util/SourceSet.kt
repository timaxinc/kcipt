package util

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.language.jvm.tasks.ProcessResources

inline fun Project.sourceSets(crossinline body: SourceSetsBuilder.() -> Unit) =
        SourceSetsBuilder(this).body()

class SourceSetsBuilder(val project: Project) {

    inline operator fun String.invoke(crossinline body: SourceSet.() -> Unit): SourceSet {
        val sourceSetName = this
        return project.sourceSets.maybeCreate(sourceSetName).apply {
            none()
            body()
        }
    }
}

fun SourceSet.none() {
    java.setSrcDirs(emptyList<String>())
    resources.setSrcDirs(emptyList<String>())
}

val SourceSet.projectDefault: Project.() -> Unit
    get() = {
        when (this@projectDefault.name) {
            "main" -> {
                java.srcDirs("src")
                @Suppress("UnstableApiUsage")
                val processResources = tasks.getByName(processResourcesTaskName) as ProcessResources
                processResources.from("resources") { include("**") }
                processResources.from("src") { include("META-INF/**", "**/*.properties") }
            }
            "test" -> {
                java.srcDirs("test", "tests")
            }
        }
    }

val SourceSet.projectDefaultKotlin: Project.() -> Unit
    get() = {
        when (this@projectDefaultKotlin.name) {
            "main" -> {
                java {
                    srcDirs("kotlin")
                }
                resources {
                    srcDirs("resources")
                }
            }
            "test" -> {
                java {
                    srcDirs("test")
                }
                resources {
                    srcDirs("testResources")
                }
            }
        }
    }

fun Project.getSourceSetsFrom(projectPath: String): SourceSetContainer {
    evaluationDependsOn(projectPath)
    return project(projectPath).sourceSets
}

val Project.sourceSets: SourceSetContainer
    get() = javaPluginConvention().sourceSets

val Project.mainSourceSet: SourceSet
    get() = sourceSets.getByName("main")

val Project.testSourceSet: SourceSet
    get() = sourceSets.getByName("test")

fun Project.useProjectDefaultKotlinSourceSet() = sourceSets {
    "main" {
        projectDefaultKotlin()
    }
    "test" {
        projectDefaultKotlin()
    }
}

val SourceSet.buildSrc: Project.() -> Unit
    get() = {
        when (this@buildSrc.name) {
            "main" -> {
                java.srcDirs("kotlin")
            }
        }
    }

fun Project.useBuildSrcSourceSet() = sourceSets {
    "main" {
        buildSrc()
    }
    "test" { }
}
