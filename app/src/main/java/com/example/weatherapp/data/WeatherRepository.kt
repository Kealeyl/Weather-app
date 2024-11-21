package com.example.weatherapp.data

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.network.CityRequest
import com.example.weatherapp.network.GeocoderRequest
import com.example.weatherapp.network.WeatherApiService

interface WeatherRepository {
    suspend fun getCityWeather(lat: Double, lon: Double): CityRequest
    suspend fun getLatLongName(cityName: String): List<GeocoderRequest>
}

class NetworkWeatherRepository(private val weatherApiService: WeatherApiService) : WeatherRepository{
    override suspend fun getCityWeather(lat: Double, lon: Double): CityRequest {
        return weatherApiService.getCityWeather(
            lat = lat,
            lon = lon,
            apiKey = BuildConfig.API_KEY,
            tempUnit = "metric",
            exclude = "minutely,alerts"
        )
    }

    override suspend fun getLatLongName(cityName: String): List<GeocoderRequest> {
        return weatherApiService.getLatLongName(
            apiKey = BuildConfig.API_KEY,
            city = cityName,
            limit = 1
        )
    }
}