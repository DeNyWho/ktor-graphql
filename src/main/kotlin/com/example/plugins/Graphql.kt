package com.example.plugins

import com.apurebase.kgraphql.GraphQL
import com.example.graphql.authSchema
import com.example.graphql.dessertSchema
import com.example.graphql.reviewSchema
import com.example.models.Dessert
import com.example.services.AuthService
import com.example.services.DessertService
import com.example.services.ReviewService
import io.ktor.application.*

fun Application.configureGraphQL() {

    install(GraphQL) {
        val dessertService = DessertService()
        val reviewService = ReviewService()
        val authService = AuthService()
        playground = true
        schema {
            dessertSchema(dessertService)
            reviewSchema(reviewService)
            authSchema(authService)
        }
    }
}