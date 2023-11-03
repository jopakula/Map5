package com.example.map5.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class PointsResponse(
    val points: List<PointResponse>,
)

@Serializable
data class PointResponse(
    val id: String,
    val data: String,
    val latitude: String,
    val longitude: String,
    val mac: String,
    val temp: String,
)