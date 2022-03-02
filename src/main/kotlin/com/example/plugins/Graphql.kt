package com.example.plugins

import com.apurebase.kgraphql.GraphQL
import com.example.graphql.dessertSchema
import com.example.models.Dessert
import com.example.services.DessertService
import io.ktor.application.*

fun Application.configureGraphQL() {

    install(GraphQL) {
        val dessertService = DessertService()
        playground = true
        schema {
            dessertSchema(dessertService)
        }
    }
}