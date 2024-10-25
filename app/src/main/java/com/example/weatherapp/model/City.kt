package com.example.weatherapp.model

import androidx.annotation.DrawableRes
import com.example.weatherapp.R

data class City(val cityName: String,
                val forecast7day: Array<WeatherDay>,
                val currentCondition: WeatherValue)


class WeatherDay(val forecast24Hour: Array<WeatherValue>, val date: String, val dayCondition: WeatherValue){
}

class WeatherValue(val temperature: Int, val condition: WeatherCondition, val time: String){

}

enum class WeatherCondition(@DrawableRes val drawableResId: Int){
    Sunny(R.drawable.baseline_wb_sunny_24),
    Cloudy(R.drawable.baseline_wb_cloudy_24),
    Rain(R.drawable.baseline_water_drop_24)
}