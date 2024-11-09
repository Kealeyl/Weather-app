package com.example.weatherapp.ui

import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.Tab
import com.example.weatherapp.data.WeatherUIState
import com.example.weatherapp.data.defaultCity
import com.example.weatherapp.data.listOfCities
import com.example.weatherapp.model.City
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WeatherAppViewModel : ViewModel() {

    // Backing property
    private val _uiState = MutableStateFlow(WeatherUIState())

    // read-only state flow
    val uiState: StateFlow<WeatherUIState> = _uiState.asStateFlow()

    private var tempCity: City = defaultCity

    init {
        _uiState.update {
            it.copy(listOfSavedCities = listOfCities)
        }
    }

    fun tabClick(clickedTab: Tab){
        _uiState.update {
            it.copy(currentScreen = clickedTab)
        }
    }

    fun clickCity(city: City) {

        if(uiState.value.currentScreen == Tab.SAVED){
            _uiState.update {
                it.copy(
                    currentSelectedCity = city,
                    currentScreen = Tab.CITYDETAIL,
                    isPreviousSavedScreen = true)
            }
        } else {
            _uiState.update {
                it.copy(
                    currentSelectedCity = city,
                    currentScreen = Tab.CITYDETAIL,
                    isPreviousSavedScreen = false)
            }
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

    fun getCity(city: City): City {
        return _uiState.value.listOfSavedCities.find { it == city } ?: defaultCity
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

    // change to delete by city
    fun deleteCity(index: Int) {
        if (index < 0 || index >= _uiState.value.listOfSavedCities.size) {
            throw IndexOutOfBoundsException("Index $index is out of bounds for size ${_uiState.value.listOfSavedCities.size}")
        }
        _uiState.update { currentState ->
            currentState.copy(
                // creates a new list that includes all cities except the one at the index
                listOfSavedCities = currentState.listOfSavedCities.filterIndexed { i, _ -> i != index }
            )
        }
    }
}