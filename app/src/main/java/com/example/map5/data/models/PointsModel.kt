package com.example.map5.data.models

import com.example.map5.data.responses.PointResponse
import kotlinx.serialization.Serializable

@Serializable
data class PointModel(
    val id: Long,
    val data: String,
    val latitude: Double,
    val longitude: Double,
    val mac: String,
    val temp: Long,
)

fun PointResponse.toModel(): PointModel = PointModel(
    id = this.id.toLong(),
    data = this.data,
    latitude = this.latitude.toDouble(),
    longitude = this.longitude.toDouble(),
    mac = this.mac,
    temp = this.temp.toLong(),
)
