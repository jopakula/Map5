package com.example.map5.data

import com.example.map5.data.models.PointModel
import com.example.map5.data.models.toModel
import com.example.map5.data.responses.PointsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

object HttpApi {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }

        defaultRequest {
            url("http://89.108.99.89:3338")
        }
    }

    suspend fun getPoints(): List<PointModel> {
        return (client.get("points").body() as PointsResponse).points.map { pointResponse ->
            pointResponse.toModel()
        }
    }
}