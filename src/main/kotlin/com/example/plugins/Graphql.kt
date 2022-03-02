package com.example.plugins

import com.apurebase.kgraphql.GraphQL
import com.example.graphql.dessertSchema
import com.example.graphql.reviewSchema
import com.example.models.Dessert
import com.example.services.DessertService
import com.example.services.ReviewService
import io.ktor.application.*

fun Application.configureGraphQL() {

    install(GraphQL) {
        val dessertService = DessertService()
        val reviewService = ReviewService()
        playground = true
        schema {
            dessertSchema(dessertService)
            reviewSchema(reviewService)
        }
    }
}