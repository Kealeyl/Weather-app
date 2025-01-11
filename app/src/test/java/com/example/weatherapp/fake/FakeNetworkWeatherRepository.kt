package com.example.weatherapp.fake

import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.network.CityRequest
import com.example.weatherapp.network.GeocoderRequest

class FakeNetworkWeatherRepository : WeatherRepository {
    override suspend fun getCityWeather(lat: Double, lon: Double): CityRequest {
        if(lat == FakeDataSource.cityRequestOttawa .lat && lon == FakeDataSource.cityRequestOttawa .lon ){
            return FakeDataSource.cityRequestOttawa
        }
        return FakeDataSource.cityRequestNull
    }

    override suspend fun getLatLongName(cityName: String): List<GeocoderRequest> {
        if(cityName == FakeDataSource.geocoderRequestOttawa[0].name){
            return FakeDataSource.geocoderRequestOttawa
        }
        return listOf()
    }
}