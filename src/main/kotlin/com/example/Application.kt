package com.example

import com.example.di.mainModule
import com.example.plugins.configureGraphQL
import com.example.plugins.configureRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.core.context.startKoin
import org.koin.ktor.ext.modules

fun main() {
    embeddedServer(Netty, port = 8060, host = "0.0.0.0") {
        configureRouting()
        startKoin {
            modules(mainModule)
        }
        configureGraphQL()
    }.start(wait = true)
}