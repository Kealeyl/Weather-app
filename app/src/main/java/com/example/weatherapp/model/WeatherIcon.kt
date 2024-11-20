package com.example.weatherapp.model

import com.example.weatherapp.R

enum class WeatherValues(val iconCode: String, val description: String) {
    CLEAR_DAY("01d", "Clear sky"),
    CLEAR_NIGHT("01n", "Clear sky"),
    PARTLY_CLOUDY_DAY("02d", "Few clouds"),
    PARTLY_CLOUDY_NIGHT("02n", "Few clouds"),
    CLOUDY("03d", "Scattered clouds"),
    RAIN("09d", "Shower rain"),
    THUNDERSTORM("11d", "Thunderstorm"),
    SNOW("13d", "Snow"),
    MIST("50d", "Mist");
}

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
        else -> {R.drawable.network_error}
    }
}