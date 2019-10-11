package util

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

fun Task.dependsOnSubProjectsTask(name: String) {
    project.subprojects.forEach {
        val task = it.tasks.findByPath(project.name + ":" + name)
        if (task != null) dependsOn(task)
    }
}

fun Task.dependsOnSubProjectsTask() {
    dependsOnSubProjectsTask(name)
}

fun Project.dependsOnSubProjects() {
    dependencies {
        subprojects.forEach {
            add("compile", project(it.path))
            project(it.path)
            add("archives", project(it.path))
        }
    }
}

fun Project.dependsOnSubjectsTasks() {
    tasks.forEach {
        it.dependsOnSubProjectsTask()
    }
}
