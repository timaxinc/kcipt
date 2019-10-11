import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import util.dependsOnSubProjects
import util.jvmTarget

version = "0.0.1"

plugins {
    base
    idea
    kotlin("jvm") version Versions.kotlin
}

allprojects {
    repositories {
        jcenter()
        mavenCentral()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://dl.bintray.com/soywiz/soywiz")
        maven("https://mvnrepository.com/")
    }
}

subprojects {
    version = rootProject.version
    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }
    kotlin {
        jvmTarget = "1.8"
    }
    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }
        "jar"(Jar::class) {
            @Suppress("UnstableApiUsage") archiveFileName.set(
                    "${rootProject.name}${this@subprojects.path.replace(
                            ":", "-"
                    )}-${this@subprojects.version}.jar"
            )
        }
    }
}

dependencies {
    dependsOnSubProjects()
}

tasks {
    "build" {
        dependsOnSubProjectsTask()
        finalizedBy("copySubProjectJars")
    }
    "clean" {
        dependsOnSubProjectsTask()
    }
    "jar"(Jar::class) {
        dependsOnSubProjectsTask()
        @Suppress("UnstableApiUsage") archiveFileName.set("${rootProject.name}-${rootProject.version}.jar")
    }
}

val copySubProjectJars = task("copySubProjectJars", Copy::class) {
    includeEmptyDirs = true
    subprojects.forEach { from("${it.buildDir.absolutePath}/libs") }
    into("$buildDir/libs")
}

val copyDependenciesJars = task("copyDependenciesJars", Copy::class) {
    includeEmptyDirs = true
    subprojects.forEach {
        @Suppress("UnstableApiUsage") from(it.configurations.archives.map {
            it.asFileTree
        })
    }
    into("$buildDir/libs")
}
