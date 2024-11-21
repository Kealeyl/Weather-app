package com.example.weatherapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Kotlin data class to store the parsed results from kotlinx.serialization


// key names in the JSON response
// ? for fields that might not be present for every request


@Serializable
// key names in the JSON response
data class CityRequest(
    val lat: Double,
    val lon: Double,
    val timezone: String?,
    @SerialName(value = "timezone_offset")
    val timezoneOffset: Int?,
    val current: Current,
    val hourly: List<Hourly>,
    val daily: List<Daily>
)

// creating additional data classes for nested JSON objects.

@Serializable
data class Current(
    val dt: Int,
    val sunrise: Int?,
    val sunset: Int?,
    val temp: Double,
    @SerialName(value = "feels_like")
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @SerialName(value = "dew_point")
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int? = null,
    @SerialName(value = "wind_speed")
    val windSpeed: Double,
    @SerialName(value = "wind_deg")
    val windDeg: Int,
    val rain: Rain?  = null,
    val snow: Snow? = null,
    @SerialName(value = "wind_gust")
    val windGust: Double? = null,
    val weather: List<Weather>
)

@Serializable
data class Rain(
    @SerialName(value = "1h")
    val hour1: Double?
)

@Serializable
data class Snow(
    @SerialName(value = "1h")
    val hour1: Double?
)


@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Hourly(
    val dt: Int,
    val temp: Double,
    @SerialName(value = "feels_like")
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @SerialName(value = "dew_point")
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int? = null,
    @SerialName(value = "wind_speed")
    val windSpeed: Double,
    @SerialName(value = "wind_deg")
    val windDeg: Int,
    val rain: Rain? = null,
    val snow: Snow? = null,
    @SerialName(value = "wind_gust")
    val windGust: Double? = null,
    val weather: List<Weather>,
    val pop: Double
)

@Serializable
data class Daily(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val moonrise: Int,
    val moonset: Int,
    @SerialName(value = "moon_phase")
    val moonPhase: Double,
    val summary: String,
    val temp: Temp,
    @SerialName(value = "feels_like")
    val feelsLike: FeelsLike,
    val pressure: Int,
    val humidity: Int,
    @SerialName(value = "dew_point")
    val dewPoint: Double,
    @SerialName(value = "wind_speed")
    val windSpeed: Double,
    @SerialName(value = "wind_deg")
    val windDeg: Int,
    @SerialName(value = "wind_gust")
    val windGust: Double? = null,
    val weather: List<Weather>,
    val clouds: Int,
    val pop: Double?,
    val rain: Double? = null,
    val snow: Double? = null,
    val uvi: Double
)

@Serializable
data class Temp(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

@Serializable
data class FeelsLike(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)