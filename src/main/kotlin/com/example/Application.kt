package com.example

import com.apurebase.kgraphql.GraphQL
import com.example.di.mainModule
import com.example.graphql.authSchema
import com.example.graphql.dessertSchema
import com.example.graphql.profileSchema
import com.example.graphql.reviewSchema
import com.example.plugins.configureGraphQL
import com.example.plugins.configureRouting
import com.example.services.AuthService
import com.example.services.DessertService
import com.example.services.ProfileService
import com.example.services.ReviewService
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.core.context.startKoin
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    configureRouting()
    startKoin {
        modules(mainModule)
    }
    configureGraphQL()
}