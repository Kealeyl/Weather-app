package com.example.weatherapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Kotlin data class to store the parsed results from kotlinx.serialization

@Serializable
// key names in the JSON response
data class CityRequest(
    val request: Request,
    val location: Location,
    val current: Current
)

// creating additional data classes for nested JSON objects.

@Serializable
data class Request(
    val type: String,
    val query: String,
    val language: String,
    val unit: String
)

@Serializable
data class Location(
    val name: String,
    val country: String,
    val region: String,
    val lat: String,
    val lon: String,
    @SerialName(value = "timezone_id")
    val timezoneId: String,
    val localtime: String,
    @SerialName(value = "localtime_epoch")
    val localtimeEpoch: Long,
    @SerialName(value = "utc_offset")
    val utcOffset: String
)

@Serializable
data class Current(
    @SerialName(value = "observation_time")
    val observationTime: String,
    val temperature: Int,
    @SerialName(value = "weather_code")
    val weatherCode: Int,
    @SerialName(value = "weather_icons")
    val weatherIcons: List<String>,
    @SerialName(value = "weather_descriptions")
    val weatherDescriptions: List<String>,
    @SerialName(value = "wind_speed")
    val windSpeed: Int,
    @SerialName(value = "wind_degree")
    val windDegree: Int,
    @SerialName(value = "wind_dir")
    val windDir: String,
    val pressure: Int,
    @SerialName(value = "precip")
    val precipitation: Double,
    val humidity: Int,
    @SerialName(value = "cloudcover")
    val cloudCover: Int,
    @SerialName(value = "feelslike")
    val feelsLike: Int,
    @SerialName(value = "uv_index")
    val uvIndex: Int,
    val visibility: Int,
    @SerialName(value = "is_day")
    val isDay: String
)