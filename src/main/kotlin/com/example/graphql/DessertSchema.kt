package com.example.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.example.models.Dessert
import com.example.models.DessertInput
import com.example.repository.DessertRepository
import com.example.services.DessertService

fun SchemaBuilder.dessertSchema(dessertService: DessertService) {

    inputType<DessertInput> {
        description = "Input of the dessert without the identifier"
    }

    type<Dessert> {
        description = "Dessert object with attributes name, description and imageUrl"
    }

    query("dessert") {
        resolver { dessertId: String ->
            try {
                dessertService.getDessert(dessertId)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("createDessert") {
        description = "Create a new dessert"
        resolver { dessertInput: DessertInput ->
            try {
                val userId = "abc"
                dessertService.createDessert(dessertInput, userId)
            } catch (e: Exception) {
                null
            }
        }
    }

    // TODO: update Dessert and delete Desert resolver

    mutation("updateDessert") {
        resolver { dessertId: String, dessertInput: DessertInput ->
            try {
                val userId = "abc"
                dessertService.updateDessert(userId, dessertId, dessertInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteDessert") {
        resolver { dessertId: String ->
            try {
                val userId = "abc"
                dessertService.deleteDessert(userId, dessertId)
            } catch (e: Exception) {
                null
            }
        }
    }
}