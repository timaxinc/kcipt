normalGradleProject()

dependencies {
    compile(project(":util"))
    compile(project(":lib"))
    compile(Deps.Jvm.kotlinScriptCommon)
    compile(Deps.Jvm.kotlinScriptJvm)
    compile(Deps.Jvm.kotlinScriptJvmHost)
    compile(Deps.Jvm.kotlinScriptCompiler)
}
