package com.example.weatherapp.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

// only include the root URL and path
private val BASE_URL = "https://api.weatherstack.com/"

interface WeatherApiService {

    // GET request specifying an endpoint to append to the baseUrl
    @GET("current")
    suspend fun getCityWeather(     // suspend to not block the calling thread
        @Query("access_key") apiKey: String,
        @Query("query") city: String
    ): CityRequest
}


// build and create a Retrofit object.
// convert the JSON object to Kotlin objects using kotlinx.serialization
private val retrofit = Retrofit.Builder().addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

// one instance of the Retrofit API service
object WeatherApi {
    val retrofitService: WeatherApiService by lazy { retrofit.create(WeatherApiService::class.java) }
}
