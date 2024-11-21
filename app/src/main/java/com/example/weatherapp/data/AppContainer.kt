package com.example.weatherapp.data

import com.example.weatherapp.network.WeatherApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// dependencies that the app requires, they are used across the whole application
interface AppContainer {
    val weatherRepository: NetworkWeatherRepository
}

// creating weatherRepository dependency

class DefaultAppContainer : AppContainer {

    // only include the root URL and path
    private val baseUrl = "https://api.openweathermap.org"

    // build and create a Retrofit object.
    // convert the JSON object to Kotlin objects using kotlinx.serialization
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: WeatherApiService by lazy { retrofit.create(WeatherApiService::class.java) }

    override val weatherRepository: NetworkWeatherRepository by lazy {
        NetworkWeatherRepository(retrofitService)
    }
}