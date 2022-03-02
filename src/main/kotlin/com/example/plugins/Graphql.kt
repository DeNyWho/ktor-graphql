package com.example.plugins

import com.apurebase.kgraphql.GraphQL
import com.example.graphql.dessertSchema
import io.ktor.application.*

fun Application.configureGraphQL() {

    install(GraphQL) {

        playground = true
        schema {
            dessertSchema()
        }
    }
}