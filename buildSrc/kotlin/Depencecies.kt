object Versions {
    val kotlin = "1.3.70-eap-263"
    val kotlinPlugin = "1.3.50"
    val kotlinCoroutines = "1.2.1"
    val klock = "1.0.0"
}

object Deps {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    val klock = "com.soywiz:klock-jvm:${Versions.klock}"

    object Jvm {
        val kotlinScriptCommon = "org.jetbrains.kotlin:kotlin-scripting-common:${Versions.kotlin}"
        val kotlinScriptJvm = "org.jetbrains.kotlin:kotlin-scripting-jvm:${Versions.kotlin}"
        val kotlinScriptJvmHost = "org.jetbrains.kotlin:kotlin-scripting-jvm-host:${Versions.kotlin}"
        val kotlinScriptCompiler = "org.jetbrains.kotlin:kotlin-scripting-compiler:${Versions.kotlin}"
    }
}
