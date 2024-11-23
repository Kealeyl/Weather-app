package com.example.weatherapp.data

import com.example.weatherapp.model.City

data class WeatherUIState (
    val listOfSavedCitiesTemp: List<City> = emptyList(),
    val listOfSavedCitiesOrderAdded: List<City> = listOfCities,
    val homeCity: City = homeCityOttawa,
    val userSearchNetwork: String = "",
    val userSearchSaved: String = "",
    val currentScreen: Tab = Tab.HOME, // dynamic navigation
    val currentSelectedCity: City = defaultCity, // for weather details
    val isPreviousSavedScreen: Boolean = false, // for nav, clicking a city from either saved or search screen
    val is24Hour: Boolean = false
)

enum class Tab{
    HOME,
    SEARCH,
    SAVED,
    CITYDETAIL,
}