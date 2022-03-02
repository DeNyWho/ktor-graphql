package com.example

import com.example.plugins.configureGraphQL
import com.example.plugins.configureRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8050, host = "0.0.0.0") {
        configureRouting()
        configureGraphQL()
    }.start(wait = true)
}