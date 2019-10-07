normalGradleProject()

dependencies {
    compile(project(":util"))
    compile(Deps.Jvm.koltinScriptCommon)
    compile(Deps.Jvm.koltinScriptJvm)
    compile(Deps.Jvm.koltinScriptJvmHost)
}
