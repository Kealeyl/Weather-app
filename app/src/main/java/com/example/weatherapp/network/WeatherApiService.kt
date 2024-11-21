package com.example.weatherapp.network

import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {
    // GET request specifying an endpoint to append to the baseUrl
    @GET("geo/1.0/direct")
    suspend fun getLatLongName(     // suspend to not block the calling thread
        @Query("q") city: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String,
    ): List<GeocoderRequest>

    @GET("data/3.0/onecall")
    suspend fun getCityWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String, // exclude minutely,alerts
        @Query("units") tempUnit: String, // metric for Celsius, imperial for Fahrenheit
        @Query("appid") apiKey: String,
    ): CityRequest
}

