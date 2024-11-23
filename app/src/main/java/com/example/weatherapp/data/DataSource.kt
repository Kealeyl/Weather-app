package com.example.weatherapp.data

import com.example.weatherapp.R
import com.example.weatherapp.model.City
import com.example.weatherapp.model.WeatherDay
import com.example.weatherapp.model.WeatherHour
import com.example.weatherapp.model.WeatherNetwork
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

val hourArray24: Array<String> = arrayOf(
    "00", "01", "02", "03", "04", "05", "06", "07",
    "08", "09", "10", "11", "12", "13", "14", "15",
    "16", "17", "18", "19", "20", "21", "22", "23"
)

val hourArray12: Array<String> = arrayOf(
    "12:00 AM", "1:00 AM", "2:00 AM", "3:00 AM", "4:00 AM",
    "5:00 AM", "6:00 AM", "7:00 AM", "8:00 AM", "9:00 AM",
    "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM",
    "3:00 PM", "4:00 PM", "5:00 PM", "6:00 PM", "7:00 PM",
    "8:00 PM", "9:00 PM", "10:00 PM", "11:00 PM"
)

val weekDayArray: Array<String> = arrayOf(
    "Sunday", "Monday", "Tuesday", "Wednesday",
    "Thursday", "Friday", "Saturday")

val weatherValues = arrayOf(
    "01d" to "Clear sky",
    "01n" to "Clear sky",
    "02d" to "Few clouds",
    "02n" to "Few clouds",
    "03d" to "Scattered clouds",
    "09d" to "Shower rain",
    "11d" to "Thunderstorm",
    "13d" to "Snow",
    "50d" to "Mist"
)

fun stringToDrawableRes(iconCode: String): Int{
    return when (iconCode) {
        "01d" -> R.drawable._01d
        "01n" -> R.drawable._01n
        "02d" -> R.drawable._02d
        "02n" -> R.drawable._02n
        "03d" -> R.drawable._03d
        "09d" -> R.drawable._09d
        "11d" -> R.drawable._11d
        "13d" -> R.drawable._13d
        "50d" -> R.drawable._50d
        else -> {
            R.drawable.network_error}
    }
}

val weatherDescriptions = weatherValues.map { it.second }.toTypedArray()
val weatherIcons = weatherValues.map { it.first }.toTypedArray()

val listOfCities = listOf(
    createRandomCity("New York"),
    createRandomCity("Singapore"),
    createRandomCity("Tokyo"),
    createRandomCity("Los Angeles"),
    createRandomCity("Portland"),
    createRandomCity("Paris"),
    createRandomCity("Sydney"),
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
    val randomInt = Random.nextInt(weatherValues.size)

    return WeatherDay(
        date = SimpleDateFormat("EEEE, dd MMMM yyyy | HH:00", Locale.getDefault()).format(Date()),
        temperature = Random.nextInt(0, 31),
        weatherIcon = weatherIcons[randomInt],
        weatherDescription = weatherDescriptions[randomInt]
    )
}

fun listOfDummy7days(): List<WeatherDay> {
    var randomInt: Int

    val listWeatherDay = mutableListOf<WeatherDay>()

    for (i in 0..6) {
        randomInt = Random.nextInt(weatherValues.size)
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
    var randomInt: Int

    val listWeatherHour = mutableListOf<WeatherHour>()

    for (i in 0..23) {
        randomInt = Random.nextInt(weatherValues.size)
        listWeatherHour.add(
            WeatherHour(
                hour = hourArray24[i],
                temperature = Random.nextInt(0, 31),
                weatherIcon = weatherIcons[randomInt],
                weatherDescription = weatherDescriptions[randomInt]
            )
        )
    }
    return listWeatherHour
}

fun listOfError24Hours(): List<WeatherHour> {
    val listWeatherHour = mutableListOf<WeatherHour>()

    for (i in 0..23) {
        listWeatherHour.add(
            WeatherHour(
                hour = hourArray24[i],
                temperature = 0,
                weatherIcon = "error",
                weatherDescription = "error"
            )
        )
    }
    return listWeatherHour
}