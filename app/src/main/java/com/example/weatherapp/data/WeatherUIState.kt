package com.example.weatherapp.data

import com.example.weatherapp.model.City

data class WeatherUIState (
    val listOfSavedCities: List<City> = listOfCities,
    val homeCity: City = homeCityOttawa,
    val userSearch: String = "",
    val currentScreen: Tab = Tab.HOME, // dynamic navigation
    val currentSelectedCity: City = defaultCity, // for weather details
    val isPreviousSavedScreen: Boolean = false // for nav, clicking a city from either saved or search screen
)

enum class Tab{
    HOME,
    SEARCH,
    SAVED,
    CITYDETAIL,
}