normalGradleProject()

dependencies {
    compile(project(":util"))
    compile(Deps.Jvm.koltinScriptRuntime)
    compile(Deps.Jvm.koltinScriptUtil)
    compile(Deps.Jvm.koltinScriptHost)
}
