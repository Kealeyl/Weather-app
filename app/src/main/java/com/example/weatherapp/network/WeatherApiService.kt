package com.example.weatherapp.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

// only include the root URL and path
private val BASE_URL = "https://api.openweathermap.org"

interface NewWeatherApiService {
    // GET request specifying an endpoint to append to the baseUrl
    @GET("geo/1.0/direct")
    suspend fun getLatLongName(     // suspend to not block the calling thread
        @Query("q") city: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String,
    ): List<GeocoderRequest>

    @GET("data/3.0/onecall")
    suspend fun getNewCityWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String, // exclude minutely,alerts
        @Query("units") tempUnit: String, // metric for Celsius, imperial for Fahrenheit
        @Query("appid") apiKey: String,

    ): CityRequest
}

// build and create a Retrofit object.
// convert the JSON object to Kotlin objects using kotlinx.serialization
private val retrofit = Retrofit.Builder().addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

// one instance of the Retrofit API service
object NewWeatherApi {
    val newRetrofitService: NewWeatherApiService by lazy { retrofit.create(NewWeatherApiService::class.java) }
}