normalGradleProject()

dependencies {
    compile(project(":util"))
    compile(project(":core"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    implementation("io.mockk:mockk:1.9.3")
    implementation("com.google.jimfs:jimfs:1.1")
    implementation("com.google.guava:guava:28.1-jre")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
