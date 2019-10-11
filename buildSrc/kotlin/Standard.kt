import org.gradle.kotlin.dsl.KotlinBuildScript
import org.gradle.kotlin.dsl.repositories
import util.deleteOutOnClean
import util.dependsOnSubProjects
import util.useProjectDefaultKotlinSourceSet

fun KotlinBuildScript.normalGradleProject() {
    repositories {
        mavenCentral()
    }

    dependsOnSubProjects()
    useProjectDefaultKotlinSourceSet()
    deleteOutOnClean()
}
