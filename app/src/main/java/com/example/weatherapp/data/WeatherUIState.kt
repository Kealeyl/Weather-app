package com.example.weatherapp.data

import com.example.weatherapp.model.City

data class WeatherUIState (
    val listOfSavedCities: List<City> = listOfCities,
    val city: City = listOfCities.first(),
    val userSearch: String = "",
    val currentScreen: Tab = Tab.HOME, // dynamic navigation
    val currentSelectedCity: City = defaultCity,
    val isPreviousSavedScreen: Boolean = false
)

enum class Tab{
    HOME,
    SEARCH,
    SAVED,
    CITYDETAIL,
}