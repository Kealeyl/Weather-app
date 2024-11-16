package com.example.weatherapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.Tab
import com.example.weatherapp.data.WeatherUIState
import com.example.weatherapp.data.arrayOfDummy7days
import com.example.weatherapp.data.defaultCity
import com.example.weatherapp.data.listOfCities
import com.example.weatherapp.model.City
import com.example.weatherapp.model.WeatherCondition
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.model.WeatherNetwork
import com.example.weatherapp.model.WeatherValue
import com.example.weatherapp.network.WeatherApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherAppViewModel : ViewModel() {

    // Backing property
    private val _uiState = MutableStateFlow(WeatherUIState())

    // read-only state flow
    val uiState: StateFlow<WeatherUIState> = _uiState.asStateFlow()

    private var tempCity: City = defaultCity

    init {
        getHomeCityWeather(_uiState.value.homeCity.cityName)
        getSavedCitiesWeather()
        _uiState.update {
            it.copy(listOfSavedCities = listOfCities)
        }
    }

    fun tabClick(clickedTab: Tab) {
        _uiState.update {
            it.copy(currentScreen = clickedTab)
        }
    }

    fun clickCity(city: City) {
        Log.d("clickCity", "Clicked city $city")
        _uiState.update {
            it.copy(
                currentSelectedCity = city,
                currentScreen = Tab.CITYDETAIL,
                isPreviousSavedScreen = true
            )
        }
    }

    fun backPressClickSearchScreen() {
        _uiState.update {
            it.copy(currentScreen = Tab.SEARCH)
        }
    }

    fun backPressClickSavedScreen() {
        _uiState.update {
            it.copy(currentScreen = Tab.SAVED)
        }
    }

    fun getCity(city: City): City? {
        return _uiState.value.listOfSavedCities.find { it == city }
    }

    fun onSearch(searched: String) {
        _uiState.update {
            it.copy(userSearch = searched)
        }
    }

    fun addCity() {
        _uiState.update {
            it.copy(
                listOfSavedCities = it.listOfSavedCities + tempCity
            )
        }
    }

    fun deleteCity(cityToDeltete: City) {

        _uiState.update {
            it.copy(
                listOfSavedCities = it.listOfSavedCities.filter { city -> city != cityToDeltete }
            )
        }
    }

    fun getWeatherForCity(city: String, getNetWorkRequestCity: (City) -> Unit) {

        viewModelScope.launch {
            try {
                val result = WeatherApi.retrofitService.getCityWeather(
                    apiKey = BuildConfig.API_KEY,
                    city = city
                )

                getNetWorkRequestCity(
                    City(
                        cityName = city,
                        currentCondition = WeatherValue(
                            temperature = result.current.temperature,
                            time = result.location.localtime,
                            condition = WeatherData(
                                weatherDescription = result.current.weatherDescriptions,
                                weatherIcons = result.current.weatherIcons,
                                drawableResId = WeatherCondition.Sunny.drawableResId
                            )
                        ),
                        forecast7day = arrayOfDummy7days(),
                        networkRequest = WeatherNetwork.Success
                    )
                )

            } catch (e: Exception) {
                Log.e("getWeatherForCity", "Error fetching weather data for $city", e)
                getNetWorkRequestCity(
                    City(
                        cityName = city,
                        currentCondition = WeatherValue(
                            temperature = 0,
                            time = "",
                            condition = WeatherData(
                                weatherDescription = emptyList(),
                                weatherIcons = emptyList(),
                                drawableResId = 0
                            )
                        ),
                        forecast7day = arrayOfDummy7days(),
                        networkRequest = WeatherNetwork.Error
                    )
                )
            }
        }
    }

    fun getSearchCityWeather(city: String) {
        getWeatherForCity(city) { netWorkRequestCity ->
            _uiState.update {
                it.copy(
                    currentSelectedCity = netWorkRequestCity,
                    currentScreen = Tab.CITYDETAIL,
                    isPreviousSavedScreen = false
                )
            }
        }
    }

    fun getHomeCityWeather(city: String) {
        getWeatherForCity(city) { netWorkRequestCity ->
            _uiState.update { it.copy(homeCity = netWorkRequestCity) }
        }
    }

    fun getSavedCitiesWeather() {
        _uiState.value.listOfSavedCities.forEachIndexed { index, city ->
            getWeatherForCity(city.cityName) { netWorkRequestCity ->

                _uiState.update { uiState ->
                    val updatedCities = uiState.listOfSavedCities.toMutableList()
                    updatedCities[index] = netWorkRequestCity
                    uiState.copy(listOfSavedCities = updatedCities) // creating new list
                }
            }
        }
    }

}