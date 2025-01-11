package com.example.weatherapp

import com.example.weatherapp.data.NetworkWeatherRepository
import com.example.weatherapp.fake.FakeDataSource
import com.example.weatherapp.fake.FakeDataSource.CITY_REQUEST_LAT
import com.example.weatherapp.fake.FakeDataSource.CITY_REQUEST_LON
import com.example.weatherapp.fake.FakeWeatherApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkWeatherRepositoryTest {

    @Test
    fun networkWeatherRepository_getLatLongName_verifyCityName() =
        runTest {
            val repository = NetworkWeatherRepository(weatherApiService = FakeWeatherApiService())
            assertEquals(FakeDataSource.geocoderRequestOttawa, repository.getLatLongName("Ottawa"))
        }

    @Test
    fun networkWeatherRepository_getCityRequest_verifyHourlyWeatherList() =
        runTest {
            val repository = NetworkWeatherRepository(weatherApiService = FakeWeatherApiService())
            assertEquals(FakeDataSource.cityRequestOttawa, repository.getCityWeather(CITY_REQUEST_LAT, CITY_REQUEST_LON))
        }
}