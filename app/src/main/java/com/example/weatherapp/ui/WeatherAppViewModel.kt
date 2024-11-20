package com.example.weatherapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.Tab
import com.example.weatherapp.data.WeatherUIState
import com.example.weatherapp.data.listOfCities
import com.example.weatherapp.model.City
import com.example.weatherapp.model.Hour24
import com.example.weatherapp.model.WeatherDay
import com.example.weatherapp.model.WeatherHour
import com.example.weatherapp.model.WeatherNetwork
import com.example.weatherapp.model.WeekDay
import com.example.weatherapp.network.Daily
import com.example.weatherapp.network.Hourly
import com.example.weatherapp.network.NewWeatherApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.round

class WeatherAppViewModel : ViewModel() {

    // Backing property
    private val _uiState = MutableStateFlow(WeatherUIState())

    // read-only state flow
    val uiState: StateFlow<WeatherUIState> = _uiState.asStateFlow()

    private val hourList = Hour24.entries.toTypedArray().map { it.hour }
    val weekDayArray = WeekDay.entries.toTypedArray().map { it.weekDay }

    init {
        getHomeCityWeather()
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

    // refactor to use map?
    fun isCityInSavedList(city: City): Boolean {
        return _uiState.value.listOfSavedCities.any {
            it == city
        }
    }

    // refactor to use map?
    fun getCityByName(cityName: String): City? {
        return _uiState.value.listOfSavedCities.find {
            it.cityName.equals(cityName, ignoreCase = true)
        }
    }

    fun onSearch(searched: String) {
        _uiState.update {
            it.copy(userSearch = searched)
        }
    }

    fun addCity(city: City) {
        _uiState.update {
            it.copy(
                listOfSavedCities = it.listOfSavedCities + city
            )
        }
    }

    fun deleteCity(cityToDelete: City) {
        _uiState.update {
            it.copy(
                listOfSavedCities = it.listOfSavedCities.filter { city -> city != cityToDelete }
            )
        }
    }

    fun getNewWeatherForCity(cityName: String, getNetWorkRequestCity: (City) -> Unit) {
        viewModelScope.launch {
            val lat: Double
            val lon: Double
            val name: String

            val city: City?

            try {
                city = getCityByName(cityName)
                if (city == null) {
                    val latLongName = NewWeatherApi.newRetrofitService.getLatLongName(
                        apiKey = BuildConfig.API_KEY,
                        city = cityName,
                        limit = 1
                    )

                    lat = latLongName[0].lat
                    lon = latLongName[0].lon
                    name = latLongName[0].name

                    Log.d(
                        "getNewWeatherForCity",
                        "Success fetching lat long data for $cityName, Lat: $lat, Lon: $lon"
                    )
                } else {
                    lat = city.lat
                    lon = city.lon
                    name = city.cityName
                }

                val result = NewWeatherApi.newRetrofitService.getNewCityWeather(
                    lat = lat,
                    lon = lon,
                    apiKey = BuildConfig.API_KEY,
                    tempUnit = "metric",
                    exclude = "minutely,alerts"
                )

                getNetWorkRequestCity(

                    if (city == null) {
                        City(
                            cityName = name,
                            currentCondition = WeatherDay(
                                weatherDescription = result.current.weather[0].description,
                                temperature = round(result.current.temp).toInt(),
                                weatherIcon = result.current.weather[0].icon,
                                date = getDate()
                            ),
                            forecast7day = create7DayForecast(result.daily),
                            forecast24hour = create24HourForecast(result.hourly),
                            networkRequest = WeatherNetwork.Success,
                            lat = lat,
                            lon = lon
                        )
                    } else {
                        city.copy(
                            currentCondition = WeatherDay(
                                weatherDescription = result.current.weather[0].description,
                                temperature = round(result.current.temp).toInt(),
                                weatherIcon = result.current.weather[0].icon,
                                date = getDate()
                            ),
                            forecast7day = create7DayForecast(result.daily),
                            forecast24hour = create24HourForecast(result.hourly),
                            networkRequest = WeatherNetwork.Success,
                        )
                    }
                )
                Log.d(
                    "getNewWeatherForCity",
                    "Success fetching weather for $cityName"
                )
            } catch (e: Exception) {
                Log.e("getWeatherForCity", "Error fetching weather data for $cityName", e)
                getNetWorkRequestCity(
                    City(
                        cityName = cityName,
                        currentCondition = WeatherDay(
                            weatherDescription = "",
                            date = getDate(),
                            temperature = 0,
                            weatherIcon = ""
                        ),
                        forecast7day = emptyList(),
                        forecast24hour = emptyList(),
                        networkRequest = WeatherNetwork.Error,
                        lat = 0.0,
                        lon = 0.0
                    )
                )
            }
        }
    }

    private fun getDate(): String {
        return SimpleDateFormat("EEEE, dd MMMM yyyy | HH:00", Locale.getDefault()).format(Date())
    }

    private fun create7DayForecast(listDaily: List<Daily>): List<WeatherDay> {


        val weekday = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())

        var index = 0

        for ((i, value) in weekDayArray.withIndex()) {
            if (value == weekday) {
                index = i
                break
            }
        }

        val listWeatherDay = mutableListOf<WeatherDay>()

        listWeatherDay.add(
            WeatherDay(
                temperature = round(listDaily[0].temp.day).toInt(),
                weatherDescription = listDaily[0].weather[0].description,
                weatherIcon = listDaily[0].weather[0].icon,
                date = "Today"
            )
        )


        for (i in 1..6) {
            listWeatherDay.add(
                WeatherDay(
                    temperature = round(listDaily[0].temp.day).toInt(),
                    weatherDescription = listDaily[i].weather[0].description
                        ?: "No data",
                    weatherIcon = listDaily[i].weather[0].icon,
                    date = weekDayArray[index++ % weekDayArray.size]
                )
            )
        }
        return listWeatherDay
    }

    private fun create24HourForecast(listHourly: List<Hourly>): List<WeatherHour> {


        val hour = SimpleDateFormat("HH", Locale.getDefault()).format(Date())

        var index = 0

        for ((i, value) in weekDayArray.withIndex()) {
            if (value == hour) {
                index = i
                break
            }
        }

        val listWeatherHour = mutableListOf<WeatherHour>()

        listWeatherHour.add(
            WeatherHour(
                temperature = round(listHourly[0].temp).toInt(),
                weatherIcon = listHourly[0].weather[0].icon,
                hour = "Now",
                weatherDescription = listHourly[0].weather[0].description
            )
        )

        for (i in 1..23) {
            listWeatherHour.add(
                WeatherHour(
                    temperature = round(listHourly[i].temp).toInt(),
                    weatherIcon = listHourly[i].weather[0].icon,
                    hour = hourList[index++ % hourList.size],
                    weatherDescription = listHourly[i].weather[0].description
                        ?: "No data"
                )
            )
        }
        return listWeatherHour
    }

    fun getSearchCityWeather(city: String) {
        getNewWeatherForCity(city) { netWorkRequestCity ->
            _uiState.update {
                it.copy(
                    currentSelectedCity = netWorkRequestCity,
                    currentScreen = Tab.CITYDETAIL,
                    isPreviousSavedScreen = false
                )
            }
        }
    }

    fun getHomeCityWeather() {
        getNewWeatherForCity(_uiState.value.homeCity.cityName) { netWorkRequestCity ->
            _uiState.update { it.copy(homeCity = netWorkRequestCity) }
        }
    }

    fun getSavedCitiesWeather() {
        _uiState.value.listOfSavedCities.forEachIndexed { index, city ->
            getNewWeatherForCity(city.cityName) { netWorkRequestCity ->

                _uiState.update { uiState ->
                    val updatedCities = uiState.listOfSavedCities.toMutableList()
                    updatedCities[index] = netWorkRequestCity
                    uiState.copy(listOfSavedCities = updatedCities) // creating new list
                }
            }
        }
    }
}