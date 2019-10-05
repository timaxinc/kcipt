object Versions {
    val kotlin = "1.3.41"
    val kotlinCoroutines = "1.2.1"
    val klaxon = "3.0.1"
    val jsoup = "1.7.2"
    val tornadofx = "1.7.16"
    val kfoenix = "0.1.3"
    val ktor = "1.2.2"
    val klock = "1.0.0"
    val kotlinSerializationRuntime = "0.11.1"
}

object Deps {

    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    //val jsoup = "org.jsoup:jsoup:${Versions.jsoup}"
    val klock = "com.soywiz:klock-jvm:${Versions.klock}"
    val kotlinSerializationRuntime =
            "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.kotlinSerializationRuntime}"

    object Jvm {
        val tornadofx = "no.tornado:tornadofx:${Versions.tornadofx}"
        val kfoenix = "com.github.bkenn:kfoenix:${Versions.kfoenix}"
        //val klaxon = "com.beust:klaxon:${Versions.klaxon}"
        val ktorServer = "io.ktor:ktor-server-core:${Versions.ktor}"
        val ktorServerWebSocket = "io.ktor:ktor-websockets:${Versions.ktor}"
        val ktorServerNetty = "io.ktor:ktor-server-netty:${Versions.ktor}"
        val ktorHTMLBuilder = "io.ktor:ktor-html-builder:${Versions.ktor}"
        val ktorClient = "io.ktor:ktor-client-core:${Versions.ktor}"
        val ktorClientJetty = "io.ktor:ktor-client-jetty:${Versions.ktor}"
        val ktorClientCio = "io.ktor:ktor-client-cio:${Versions.ktor}"
    }
}
