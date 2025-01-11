package com.example.weatherapp.fake

import com.example.weatherapp.network.CityRequest
import com.example.weatherapp.network.Current
import com.example.weatherapp.network.Daily
import com.example.weatherapp.network.FeelsLike
import com.example.weatherapp.network.GeocoderRequest
import com.example.weatherapp.network.Hourly
import com.example.weatherapp.network.Snow
import com.example.weatherapp.network.Temp
import com.example.weatherapp.network.Weather

object FakeDataSource {

    private val weather = Weather(
        id = 601,
        main = "Snow",
        description = "snow",
        icon = "13n"
    )

    const val CITY_REQUEST_LAT = 41.3517
    const val CITY_REQUEST_LON = -88.8454

    val cityRequestOttawa = CityRequest(
        lat = CITY_REQUEST_LAT,
        lon = CITY_REQUEST_LON,
        timezone = "America/Chicago",
        timezoneOffset = -21600,
        current = Current(
            dt = 1736511190,
            sunrise = 1736515235,
            sunset = 1736549103,
            temp = -3.02,
            feelsLike = -7.87,
            pressure = 1012,
            humidity = 94,
            dewPoint = -3.75,
            uvi = 0.0,
            clouds = 100,
            visibility = 62,
            windSpeed = 3.84,
            windDeg = 205,
            windGust = 8.0,
            weather = listOf(weather),
            rain = null,
            snow = Snow(hour1 = 0.82),
        ),
        hourly = Array(48) {
            Hourly(
                dt = 1736510400,
                temp = -3.02,
                feelsLike = -7.87,
                pressure = 1017,
                humidity = 94,
                dewPoint = -3.75,
                uvi = 0.0,
                clouds = 100,
                visibility = 62,
                windSpeed = 3.84,
                windDeg = 205,
                windGust = 9.42,
                weather = listOf(weather),
                pop = 1.0,
                rain = null,
                snow = Snow(hour1 = 0.52)
            )
        }.toList(),
        daily = Array(7) {
            Daily(
                dt = 1736532000,
                sunrise = 1736515235,
                sunset = 1736549103,
                moonrise = 1736536920,
                moonset = 1736504640,
                moonPhase = 0.39,
                summary = "Expect a day of partly cloudy with snow",
                temp = Temp(
                    day = -0.96,
                    min = -4.9,
                    max = -0.66,
                    night = -3.21,
                    eve = -2.27,
                    morn = -3.02
                ),
                feelsLike = FeelsLike(
                    day = -5.48,
                    night = -7.65,
                    eve = -6.29,
                    morn = -6.29
                ),
                pressure = 1013,
                humidity = 90,
                dewPoint = -2.58,
                windSpeed = -2.58,
                windDeg = 213,
                windGust = 12.96,
                weather = listOf(weather),
                clouds = 100,
                pop = 1.0,
                rain = null,
                snow = 5.75,
                uvi = 1.29
            )
        }.toList()
    )

    val cityRequestNull = CityRequest(
        lat = 0.0,
        lon = 0.0,
        timezone = null,
        timezoneOffset = null,
        current = Current(
            dt = 0,
            sunrise = null,
            sunset = null,
            temp = 0.0,
            feelsLike = 0.0,
            pressure = 0,
            humidity = 0,
            dewPoint = 0.0,
            uvi = 0.0,
            clouds = 0,
            visibility = null,
            windSpeed = 0.0,
            windDeg = 0,
            windGust = null,
            weather = listOf(),
            rain = null,
            snow = null,
        ),
        hourly = listOf(
            Hourly(
                dt = 0,
                temp = 0.0,
                feelsLike = 0.0,
                pressure = 0,
                humidity = 0,
                dewPoint = 0.0,
                uvi = 0.0,
                clouds = 0,
                visibility = null,
                windSpeed = 0.0,
                windDeg = 0,
                windGust = null,
                weather = listOf(),
                pop = 0.0,
                rain = null,
                snow = null
            )
        ),
        daily = listOf(
            Daily(
                dt = 0,
                sunrise = 0,
                sunset = 0,
                moonrise = 0,
                moonset = 0,
                moonPhase = 0.0,
                summary = "",
                temp = Temp(
                    day = 0.0,
                    min = 0.0,
                    max = 0.0,
                    night = 0.0,
                    eve = 0.0,
                    morn = 0.0
                ),
                feelsLike = FeelsLike(
                    day = 0.0,
                    night = 0.0,
                    eve = 0.0,
                    morn = 0.0
                ),
                pressure = 0,
                humidity = 0,
                dewPoint = 0.0,
                windSpeed = 0.0,
                windDeg = 0,
                windGust = 0.0,
                weather = listOf(),
                clouds = 0,
                pop = null,
                rain = null,
                snow = null,
                uvi = 1.29
            )
        )
    )

    val geocoderRequestOttawa = listOf(
        GeocoderRequest(
            name = "Ottawa",
            localNames = mapOf("os" to "Оттавæ", "es" to "Ottawa"),
            lat = CITY_REQUEST_LAT,
            lon = CITY_REQUEST_LON,
            country = "CA",
            state = "Ontario"
        ),
        GeocoderRequest(
            name = "Ottawa",
            localNames = null,
            lat = 41.3516628,
            lon = -88.845436,
            country = "US",
            state = "Illinois"
        )
    )
}