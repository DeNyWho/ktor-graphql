package com.example.plugins

import com.apurebase.kgraphql.GraphQL
import com.example.graphql.authSchema
import com.example.graphql.dessertSchema
import com.example.graphql.profileSchema
import com.example.graphql.reviewSchema
import com.example.services.AuthService
import com.example.services.DessertService
import com.example.services.ProfileService
import com.example.services.ReviewService
import io.ktor.application.*

fun Application.configureGraphQL() {

    install(GraphQL) {
        val dessertService = DessertService()
        val reviewService = ReviewService()
        val authService = AuthService()
        val profileService = ProfileService()
        playground = true
        context { call ->
            authService.verifyToken(call)?.let { +it }
            +log
            +call
        }
        schema {
            dessertSchema(dessertService)
            reviewSchema(reviewService)
            authSchema(authService)
            profileSchema(profileService)
        }
    }
}