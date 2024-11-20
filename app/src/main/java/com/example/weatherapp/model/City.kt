package com.example.weatherapp.model

import androidx.annotation.DrawableRes
import com.example.weatherapp.R

data class City(
    val cityName: String,
    val forecast7day: List<WeatherDay>,
    val forecast24hour: List<WeatherHour>,
    val currentCondition: WeatherDay,
    val networkRequest: WeatherNetwork,
    val lat: Double,
    val lon: Double
)

data class WeatherDay(
    val weatherDescription: String,
    val temperature: Int,
    val weatherIcon: String,
    val date: String // Week day name for 7 day forecast or Full date for current day
)

data class WeatherHour(
    val weatherDescription: String,
    val temperature: Int,
    val weatherIcon: String,
    val hour: String
)

// save the different states/status: loading, success, and failure
// makes values WeatherNetwork object can have exhaustive
sealed interface WeatherNetwork {
    object Success : WeatherNetwork
    object Error : WeatherNetwork // one instance of each state
    object Loading : WeatherNetwork
}