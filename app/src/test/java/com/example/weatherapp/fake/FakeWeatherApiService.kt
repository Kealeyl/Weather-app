package com.example.weatherapp.fake

import com.example.weatherapp.network.CityRequest
import com.example.weatherapp.network.GeocoderRequest
import com.example.weatherapp.network.WeatherApiService

class FakeWeatherApiService : WeatherApiService {
    override suspend fun getLatLongName(
        city: String,
        limit: Int,
        apiKey: String
    ): List<GeocoderRequest> {

        if(city == FakeDataSource.geocoderRequestOttawa [0].name){
            return FakeDataSource.geocoderRequestOttawa
        }
        return listOf()
    }

    override suspend fun getCityWeather(
        lat: Double,
        lon: Double,
        exclude: String,
        tempUnit: String,
        apiKey: String
    ): CityRequest {

        if(lat == FakeDataSource.cityRequestOttawa.lat && lon == FakeDataSource.cityRequestOttawa.lon ){
            return FakeDataSource.cityRequestOttawa
        }

        return FakeDataSource.cityRequestNull
    }
}