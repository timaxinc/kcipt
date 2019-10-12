import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import util.dependsOnSubProjects
import util.dependsOnSubProjectsTask
import util.jvmTarget

version = "0.0.1"

plugins {
    base
    idea
    kotlin("jvm") version Versions.kotlinPlugin
}

allprojects {
    repositories {
        jcenter()
        mavenCentral()
        maven("https://kotlin.bintray.com/kotlin-dev")
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
        finalizedBy("afterBuild")
    }
    "clean" {
        dependsOnSubProjectsTask()
    }
    "jar"(Jar::class) {
        dependsOnSubProjectsTask()
        @Suppress("UnstableApiUsage") archiveFileName.set("${rootProject.name}-${rootProject.version}.jar")
    }
}

val afterBuild = task("afterBuild") {
    dependsOn("copySubProjectJars")
    dependsOn("copyDependenciesJars")
    dependsOn("copyStaticIntoDist")
    dependsOn("copyLibsIntoDist")
    dependsOn("copyDistIntoRun")
}

val copySubProjectJars = task("copySubProjectJars", Copy::class) {
    includeEmptyDirs = true
    subprojects.forEach { from("${it.buildDir.absolutePath}/libs") }
    into("$buildDir/libs")
}

val copyDependenciesJars = task("copyDependenciesJars", Copy::class) {
    includeEmptyDirs = true
    subprojects.forEach {
        from(it.configurations.compile.map {
            it.asFileTree
        })
    }
    into("$buildDir/libs")
}

val copyDependenciesJars = task("copyStaticIntoDist", Sync::class) {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from("static")
    into("$buildDir/dist")
}

val copyLibsIntoDist = task("copyLibsIntoDist", Sync::class) {
    dependsOn("copySubProjectJars")
    dependsOn("copyDependenciesJars")
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from("$buildDir/libs")
    into("$buildDir/dist/lib")
}

val copyDistIntoRun = task("copyDistIntoRun", Sync::class) {
    dependsOn("copyStaticIntoDist")
    dependsOn("copyLibsIntoDist")
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from("$buildDir/dist")
    into("run")
}

normalGradleProject()
