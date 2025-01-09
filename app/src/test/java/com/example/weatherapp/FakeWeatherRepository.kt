package com.example.weatherapp

import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.network.CityRequest
import com.example.weatherapp.network.Current
import com.example.weatherapp.network.Daily
import com.example.weatherapp.network.FeelsLike
import com.example.weatherapp.network.GeocoderRequest
import com.example.weatherapp.network.Hourly
import com.example.weatherapp.network.Temp
import com.example.weatherapp.network.Weather

class FakeWeatherRepository : WeatherRepository {
    override suspend fun getCityWeather(lat: Double, lon: Double): CityRequest {

        val fakeCityRequest = CityRequest(
            lat = 45.4215,
            lon = -75.6972,
            timezone = "America/Toronto",
            timezoneOffset = -18000,
            current = Current(
                dt = 1672531200,
                sunrise = null,
                sunset = null,
                temp = -5.0,
                feelsLike = -10.0,
                pressure = 1012,
                humidity = 85,
                dewPoint = -7.0,
                uvi = 0.0,
                clouds = 75,
                visibility = 10000,
                windSpeed = 5.0,
                windDeg = 180,
                rain = null,
                snow = null,
                windGust = 8.0,
                weather = listOf(
                    Weather(
                        id = 800,
                        main = "Clear",
                        description = "clear sky",
                        icon = "01d"
                    )
                )
            ),
            hourly = listOf(
                Hourly(
                    dt = 1672534800,
                    temp = -5.0,
                    feelsLike = -10.0,
                    pressure = 1012,
                    humidity = 85,
                    dewPoint = -7.0,
                    uvi = 0.0,
                    clouds = 75,
                    visibility = 10000,
                    windSpeed = 5.0,
                    windDeg = 180,
                    rain = null,
                    snow = null,
                    windGust = 8.0,
                    weather = listOf(
                        Weather(
                            id = 800,
                            main = "Clear",
                            description = "clear sky",
                            icon = "01d"
                        )
                    ),
                    pop = 0.0
                )
            ),
            daily = listOf(
                Daily(
                    dt = 1672531200,
                    sunrise = 1672556400,
                    sunset = 1672599600,
                    moonrise = 1672545600,
                    moonset = 1672592400,
                    moonPhase = 0.5,
                    summary = "Sunny with scattered clouds",
                    temp = Temp(
                        day = -2.0,
                        min = -10.0,
                        max = 0.0,
                        night = -5.0,
                        eve = -3.0,
                        morn = -8.0
                    ),
                    feelsLike = FeelsLike(
                        day = -8.0,
                        night = -10.0,
                        eve = -7.0,
                        morn = -12.0
                    ),
                    pressure = 1012,
                    humidity = 85,
                    dewPoint = -7.0,
                    windSpeed = 5.0,
                    windDeg = 180,
                    windGust = 8.0,
                    weather = listOf(
                        Weather(
                            id = 800,
                            main = "Clear",
                            description = "clear sky",
                            icon = "01d"
                        )
                    ),
                    clouds = 50,
                    pop = 0.1,
                    rain = null,
                    snow = 0.0,
                    uvi = 0.5
                )
            )
        )

        return fakeCityRequest
    }

    override suspend fun getLatLongName(cityName: String): List<GeocoderRequest> {
        val fakeGeocoderRequest = GeocoderRequest(
            name = "Ottawa",
            localNames = null,
            lat = 45.4215,
            lon = -75.6972,
            country = "Canada",
            state = "Ontario"
        )
        return listOf(fakeGeocoderRequest)
    }
}