package com.example.weatherapp.data

import com.example.weatherapp.model.City
import com.example.weatherapp.model.Hour24
import com.example.weatherapp.model.WeatherDay
import com.example.weatherapp.model.WeatherHour
import com.example.weatherapp.model.WeatherNetwork
import com.example.weatherapp.model.WeatherValues
import com.example.weatherapp.model.WeekDay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

val listOfCities =
    listOf(
        createRandomCity("New York"),
        createRandomCity("Singapore"),
        createRandomCity("Tokyo"),
        createRandomCity("London"),
        createRandomCity("Paris"),
        createRandomCity("Sydney"),
        createRandomCity("Moscow"),
        createRandomCity("Berlin"),
        createRandomCity("Toronto"),
        createRandomCity("Beijing"),
        createRandomCity("Dubai"),
        createRandomCity("Rome"),
        createRandomCity("Barcelona"),
        createRandomCity("Mumbai"),
        createRandomCity("Hong Kong"),
        createRandomCity("Seoul"),
        createRandomCity("Bangkok"),
        createRandomCity("Istanbul"),
        createRandomCity("Mexico City"),
        createRandomCity("Cairo"),
        createRandomCity("Buenos Aires"),
        createRandomCity("Cape Town"),
        createRandomCity("Los Angeles"),
        createRandomCity("Chicago"),
        createRandomCity("San Francisco"),
        createRandomCity("Rio de Janeiro"),
        createRandomCity("Santiago"),
        createRandomCity("Lima"),
        createRandomCity("Jakarta"),
        createRandomCity("Kuala Lumpur"),
        createRandomCity("Hanoi"),
        createRandomCity("Manila"),
        createRandomCity("Warsaw"),
        createRandomCity("Lisbon"),
        createRandomCity("Athens"),
        createRandomCity("Vienna"),
        createRandomCity("Prague"),
        createRandomCity("Budapest"),
        createRandomCity("Oslo"),
        createRandomCity("Stockholm"),
        createRandomCity("Helsinki"),
        createRandomCity("Copenhagen"),
        createRandomCity("Reykjavik"),
        createRandomCity("Zurich"),
        createRandomCity("Geneva"),
        createRandomCity("Venice"),
        createRandomCity("Edinburgh"),
        createRandomCity("Glasgow"),
        createRandomCity("Dublin"),
        createRandomCity("Brussels")
    )

val defaultCity = createRandomCity("Default")

val homeCityOttawa = createRandomCity("Ottawa")

fun createRandomCity(cityName: String): City {
    return City(
        cityName = cityName,
        currentCondition = createRandomWeatherDayForCurrentWeather(),
        forecast7day = listOfDummy7days(),
        forecast24hour = listOfDummy24Hours(),
        lat = null,
        lon = null,
        networkRequest = WeatherNetwork.Loading
    )
}

fun createRandomWeatherDayForCurrentWeather(): WeatherDay {
    val weatherDescriptions = WeatherValues.entries.toTypedArray().map { it.description }
    val weatherIcons = WeatherValues.entries.toTypedArray().map { it.iconCode }

    val randomInt = Random.nextInt(WeatherValues.entries.size)

    return WeatherDay(
        date = SimpleDateFormat("EEEE, dd MMMM yyyy | HH:00", Locale.getDefault()).format(Date()),
        temperature = Random.nextInt(0, 31),
        weatherIcon = weatherIcons[randomInt],
        weatherDescription = weatherDescriptions[randomInt]
    )
}

fun listOfDummy7days(): List<WeatherDay> {
    val weekDayArray = WeekDay.entries.toTypedArray().map { it.weekDay }
    val weatherDescriptions = WeatherValues.entries.toTypedArray().map { it.description }
    val weatherIcons = WeatherValues.entries.toTypedArray().map { it.iconCode }

    var randomInt: Int

    val listWeatherDay = mutableListOf<WeatherDay>()

    for (i in 0..6) {
        randomInt = Random.nextInt(WeatherValues.entries.size)
        listWeatherDay.add(
            WeatherDay(
                date = weekDayArray[i],
                temperature = Random.nextInt(0, 31),
                weatherIcon = weatherIcons[randomInt],
                weatherDescription = weatherDescriptions[randomInt]
            )
        )
    }
    return listWeatherDay
}

fun listOError7days(): List<WeatherDay> {
    val weekDayArray = WeekDay.entries.toTypedArray().map { it.weekDay }

    val listWeatherDay = mutableListOf<WeatherDay>()

    for (i in 0..6) {
        listWeatherDay.add(
            WeatherDay(
                date = weekDayArray[i],
                temperature = 0,
                weatherIcon = "error",
                weatherDescription = "error"
            )
        )
    }
    return listWeatherDay
}

fun listOfDummy24Hours(): List<WeatherHour> {

    val hourArray = Hour24.entries.toTypedArray().map { it.hour }
    val weatherDescriptions = WeatherValues.entries.toTypedArray().map { it.description }
    val weatherIcons = WeatherValues.entries.toTypedArray().map { it.iconCode }

    var randomInt: Int

    val listWeatherHour = mutableListOf<WeatherHour>()

    for (i in 0..23) {
        randomInt = Random.nextInt(WeatherValues.entries.size)
        listWeatherHour.add(
            WeatherHour(
                hour = hourArray[i],
                temperature = Random.nextInt(0, 31),
                weatherIcon = weatherIcons[randomInt],
                weatherDescription = weatherDescriptions[randomInt]
            )
        )
    }
    return listWeatherHour
}

fun listOfError24Hours(): List<WeatherHour> {
    val hourArray = Hour24.entries.toTypedArray().map { it.hour }

    val listWeatherHour = mutableListOf<WeatherHour>()

    for (i in 0..23) {
        listWeatherHour.add(
            WeatherHour(
                hour = hourArray[i],
                temperature = 0,
                weatherIcon = "error",
                weatherDescription = "error"
            )
        )
    }
    return listWeatherHour
}