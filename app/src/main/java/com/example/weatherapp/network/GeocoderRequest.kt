package com.example.weatherapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocoderRequest(
    val name: String,
    @SerialName(value = "local_names")
    val localNames: Map<String, String>? = null, // key values pairs
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String? = null,
)