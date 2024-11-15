package com.example.weatherapp.data

import com.example.weatherapp.model.City
import com.example.weatherapp.model.WeatherCondition
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.model.WeatherDay
import com.example.weatherapp.model.WeatherNetwork
import com.example.weatherapp.model.WeatherValue

val listOfHours24HourTime = listOf(
    "00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
    "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
    "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
    "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"
)

val listOfHoursRegularTime = listOf(
    "12" to "AM", "1" to "AM", "2" to "AM", "3" to "AM", "4" to "AM", "5" to "AM",
    "6" to "AM", "7" to "AM", "8" to "AM", "9" to "AM", "10" to "AM", "11" to "AM",
    "12" to "PM", "1" to "PM", "2" to "PM", "3" to "PM", "4" to "PM", "5" to "PM",
    "6" to "PM", "7" to "PM", "8" to "PM", "9" to "PM", "10" to "PM", "11" to "PM"
)

val listOfCities =
    listOf(
        City(
            "New York",
            arrayOfDummy7days(),
            WeatherValue(
                20,
                createRandomWeatherData(),
                "4:00"
            ),
            networkRequest = WeatherNetwork.Loading
        ),
        City(
            "Singapore",
            arrayOfDummy7days(),
            WeatherValue(20, createRandomWeatherData(), "1:00"),
            networkRequest = WeatherNetwork.Loading
        ),
        City(
            "Tokyo",
            arrayOfDummy7days(),
            WeatherValue(30, createRandomWeatherData(), "10:00"),
            networkRequest = WeatherNetwork.Loading
        ),
    )

val defaultCity = City(
    "Default",
    arrayOfDummy7days(),
    WeatherValue(-1, createRandomWeatherData(), "00:00 PM"),
    networkRequest = WeatherNetwork.Loading
)

val homeCityOttawa = City(
    "Ottawa",
    arrayOfDummy7days(),
    WeatherValue(-1, createRandomWeatherData(), "00:00 PM"),
    networkRequest = WeatherNetwork.Loading
)

fun createRandomWeatherData(): WeatherData {
    val conditions = WeatherCondition.values()

    return WeatherData(
        weatherDescription = listOf(conditions[conditions.indices.random()].name),
        weatherIcons = listOf(conditions[conditions.indices.random()].weatherIcon),
        drawableResId = conditions[conditions.indices.random()].drawableResId)
}

fun arrayOfDummy7days(): Array<WeatherDay> {

    val forecast = arrayOfDummy24Hours()
    val weekDays = DaysOfTheWeek.values()

    return Array(7) {
        WeatherDay(
            forecast24Hour = forecast,
            date = weekDays[it % weekDays.size].name,
            dayCondition = forecast[0]
        )
    }
}

fun arrayOfDummy24Hours(): Array<WeatherValue> {

    return Array(24) {
        WeatherValue(it + 1, createRandomWeatherData(), listOfHours24HourTime[it])
    }
}

enum class DaysOfTheWeek {
    Sunday,
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday
}