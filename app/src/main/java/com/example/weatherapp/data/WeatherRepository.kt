package com.example.weatherapp.data

import com.example.weatherapp.network.CityRequest
import com.example.weatherapp.network.GeocoderRequest

interface WeatherRepository {
    suspend fun getCityWeather(lat: Double, lon: Double): CityRequest
    suspend fun getLatLongName(cityName: String): List<GeocoderRequest>
}

