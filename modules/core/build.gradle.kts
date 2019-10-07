normalGradleProject()

dependencies {
    compile(project(":util"))
    compile(Deps.Jvm.kotlinScriptCommon)
    compile(Deps.Jvm.kotlinScriptJvm)
    compile(Deps.Jvm.kotlinScriptJvmHost)
}
