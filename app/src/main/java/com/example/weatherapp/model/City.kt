package com.example.weatherapp.model

import androidx.annotation.DrawableRes
import com.example.weatherapp.R

data class City(
    var cityName: String,
    val forecast7day: Array<WeatherDay>,
    val currentCondition: WeatherValue,
    val networkRequest: WeatherNetwork
) {
    override fun toString(): String {
        return "City(cityName='$cityName', currentCondition=$currentCondition, networkRequest=$networkRequest)"
    }
}

class WeatherDay(
    val forecast24Hour: Array<WeatherValue>,
    val date: String,
    val dayCondition: WeatherValue
)

class WeatherValue(val temperature: Int, val condition: WeatherData, val time: String) {
    override fun toString(): String {
        return "WeatherValue(temperature=$temperature, condition=$condition, time='$time')"
    }
}

class WeatherData(
    val weatherDescription: List<String>,
    val weatherIcons: List<String>,
    @DrawableRes val drawableResId: Int // temporary
){
    override fun toString(): String {
        return "WeatherData(weatherDescription=$weatherDescription, weatherIcons=$weatherIcons, drawableResId=$drawableResId)"
    }
}

enum class WeatherCondition(val weatherIcon: String, @DrawableRes val drawableResId: Int) {
    Sunny(
        "https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0008_clear_sky_night.png",
        R.drawable.baseline_wb_sunny_24
    ),
    Cloudy(
        "https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0008_clear_sky_night.png",
        R.drawable.baseline_wb_cloudy_24
    ),
    Rain(
        "https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0008_clear_sky_night.png",
        R.drawable.baseline_water_drop_24
    )
}

// save the different states/status: loading, success, and failure
// makes values WeatherNetwork object can have exhaustive
sealed interface WeatherNetwork {
    object Success : WeatherNetwork
    object Error : WeatherNetwork // one instance of each state
    object Loading : WeatherNetwork
}