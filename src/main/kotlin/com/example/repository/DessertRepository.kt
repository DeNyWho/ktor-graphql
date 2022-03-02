package com.example.repository

import com.example.models.Dessert
import com.example.models.DessertsPage
import com.example.models.PagingInfo
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection
import org.litote.kmongo.skip

class DessertRepository(client: MongoClient) : RepositoryInterface<Dessert> {
    override lateinit var col: MongoCollection<Dessert>

    init {
        val database = client.getDatabase("test")
        col = database.getCollection<Dessert>("Dessert")
    }

    fun getDessertsPage(page: Int, size: Int): DessertsPage {
        try {
            val skips = page * size
            val res = col.find().skip(skips).limit(size)
                ?: throw Exception("No desserts exist")
            val results = res.asIterable().map { it }
            val totalDesserts = col.estimatedDocumentCount()
            val totalPages = (totalDesserts / size) + 1
            val next = if (results.isNotEmpty()) page + 1 else null
            val prev = if (page > 0) page - 1 else null
            val info = PagingInfo(totalDesserts.toInt(), totalPages.toInt(), next, prev)
            return DessertsPage(results, info)
        } catch (t: Throwable) {
            throw Exception("Cannot get desserts page")
        }
    }

    fun getDessertsByUserId(userId: String): List<Dessert> {
        return try {
            col.find(Dessert::userId eq userId).asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("cannot get user desserts")
        }
    }
}