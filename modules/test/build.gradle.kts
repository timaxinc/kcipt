normalGradleProject()

dependencies {
    compile(project(":util"))
    compile(project(":core"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
}

tasks.test {
    @Suppress("UnstableApiUsage")
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
