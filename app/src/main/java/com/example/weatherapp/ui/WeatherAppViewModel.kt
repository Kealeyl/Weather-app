package com.example.weatherapp.ui

//import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherapp.WeatherAppApplication
import com.example.weatherapp.data.Tab
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.WeatherUIState
import com.example.weatherapp.data.hourArray12
import com.example.weatherapp.data.hourArray24
import com.example.weatherapp.data.listOError7days
import com.example.weatherapp.data.listOfCities
import com.example.weatherapp.data.listOfError24Hours
import com.example.weatherapp.data.weekDayArray
import com.example.weatherapp.model.City
import com.example.weatherapp.model.WeatherDay
import com.example.weatherapp.model.WeatherHour
import com.example.weatherapp.model.WeatherNetwork
import com.example.weatherapp.network.Current
import com.example.weatherapp.network.Daily
import com.example.weatherapp.network.Hourly
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.round

class WeatherAppViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    // Backing property
    private val _uiState = MutableStateFlow(WeatherUIState())

    // read-only state flow
    val uiState: StateFlow<WeatherUIState> = _uiState.asStateFlow()

    init {
        getHomeCityWeather()
        getSavedCitiesWeather()
        _uiState.update {
            it.copy(listOfSavedCitiesOrderAdded = listOfCities)
        }
    }

    fun tabClick(clickedTab: Tab) {
        if (_uiState.value.userSearchSaved.isNotEmpty()) {
            onSearchSaved("")
        }
        _uiState.update {
            it.copy(currentScreen = clickedTab, userSearchNetwork = "")
        }
    }

    fun clickCity(city: City) {
     //   Log.d("clickCity", "Clicked city $city")
        _uiState.update {
            it.copy(
                currentSelectedCity = city,
                currentScreen = Tab.CITYDETAIL,
                isPreviousSavedScreen = true
            )
        }
    }

    fun onTimeCardClick() {
        val is24Hours = !_uiState.value.is24Hour
        val date = getDate(is24Hours)
        val updatedCityHours = _uiState.value.listOfSavedCitiesOrderAdded.map { city ->
            city.copy(
                forecast24hour = change24Hour(city.forecast24hour, is24Hours),
                currentCondition = city.currentCondition.copy(date = date)
            )
        }
        _uiState.update {
            it.copy(
                is24Hour = is24Hours,
                homeCity = it.homeCity.copy(
                    currentCondition = it.homeCity.currentCondition.copy(date = date),
                    forecast24hour = change24Hour(it.homeCity.forecast24hour, is24Hours)
                ), listOfSavedCitiesOrderAdded = updatedCityHours
            )
        }
    }

    fun backPressClickSearchScreen() {
        _uiState.update {
            it.copy(currentScreen = Tab.SEARCH, userSearchNetwork = "")
        }
    }

    fun backPressClickSavedScreen() {
        _uiState.update {
            it.copy(currentScreen = Tab.SAVED)
        }
    }

    fun getCity(city: City): City? {
        return _uiState.value.listOfSavedCitiesOrderAdded.find { it == city }
    }

    // refactor to use map?
    fun isCityInSavedList(city: City): Boolean {
        return _uiState.value.listOfSavedCitiesOrderAdded.any {
            it == city
        }
    }

    // refactor to use map?
    private fun getCityByName(cityName: String): City? {

        if (_uiState.value.homeCity.cityName.equals(cityName, ignoreCase = true)) {
            return _uiState.value.homeCity
        }

        return _uiState.value.listOfSavedCitiesOrderAdded.find {
            it.cityName.equals(cityName, ignoreCase = true)
        }
    }

    fun onSearchNetwork(searched: String) {
        _uiState.update {
            it.copy(userSearchNetwork = searched)
        }
    }

    fun onSearchSaved(searched: String) {

     //   Log.d("inSearchSave", "Searched: $searched")

        val listOfSavedOrder: List<City> = _uiState.value.listOfSavedCitiesOrderAdded

        if (searched.isBlank()) {
          //  Log.d("inSearchSave blank", "Searched: $searched")
            _uiState.update {
                it.copy(
                    userSearchSaved = searched,
                    listOfSavedCitiesOrderAdded = it.listOfSavedCitiesTemp,
                    listOfSavedCitiesTemp = emptyList()
                )
            }
        } else {
            // get cities that start with string
            val listOfMatches: List<City> =
                _uiState.value.listOfSavedCitiesOrderAdded.filter { city ->
                    city.cityName.startsWith(searched, ignoreCase = true)
                }

            _uiState.update {
                it.copy(
                    userSearchSaved = searched,
                    listOfSavedCitiesOrderAdded = (
                            listOfMatches.sortedBy { city -> city.cityName } + it.listOfSavedCitiesOrderAdded.filter { city ->
                                city !in listOfMatches
                            }),
                    listOfSavedCitiesTemp = if (it.listOfSavedCitiesTemp.isEmpty()) {
                        listOfSavedOrder  // Only saved original when first letter is searched
                    } else {
                        it.listOfSavedCitiesTemp
                    }
                )
            }
        }
    }


    fun addCity(city: City) {
        _uiState.update {
            it.copy(
                listOfSavedCitiesOrderAdded = it.listOfSavedCitiesOrderAdded + city
            )
        }
    }

    fun deleteCity(cityToDelete: City) {
        _uiState.update {
            it.copy(
                listOfSavedCitiesOrderAdded = it.listOfSavedCitiesOrderAdded.filter { city -> city != cityToDelete }
            )
        }
    }

    private fun createWeatherIconURL(icon: String): String {
        return "https://openweathermap.org/img/wn/${icon}@4x.png"
    }

    fun getNewWeatherForCity(cityName: String, getNetWorkRequestCity: (City) -> Unit) {
        viewModelScope.launch {
            val lat: Double
            val lon: Double
            val name: String
            val city: City? = getCityByName(cityName)

            try {
                if (city == null || city.lat == null || city.lon == null) {
                    val latLongName = weatherRepository.getLatLongName(cityName)

                    lat = latLongName[0].lat
                    lon = latLongName[0].lon
                    name = latLongName[0].name

//                    Log.d(
//                        "APIRequest",
//                        "Success fetching lat long data for $cityName, Lat: $lat, Lon: $lon"
//                    )
                } else {
                    lat = city.lat
                    lon = city.lon
                    name = city.cityName
                }
            } catch (e: Exception) {
               // Log.e("APIRequest", "Error fetching lat long data for $cityName: ${e.message}", e)
                getNetWorkRequestCity(
                    createErrorCity(cityName)
                )
                return@launch
            }

            try {
                val result = weatherRepository.getCityWeather(lat, lon)

                getNetWorkRequestCity(

                    if (city == null) {
                        City(
                            cityName = name,
                            currentCondition = createWeatherDay(result.current),
                            forecast7day = create7DayForecast(result.daily),
                            forecast24hour = create24HourForecast(result.hourly),
                            networkRequest = WeatherNetwork.Success,
                            lat = lat,
                            lon = lon
                        )
                    } else {
                        city.copy(
                            currentCondition = createWeatherDay(result.current),
                            forecast7day = create7DayForecast(result.daily),
                            forecast24hour = create24HourForecast(result.hourly),
                            networkRequest = WeatherNetwork.Success,
                            lat = lat,
                            lon = lon
                        )
                    }
                )
//                Log.d(
//                    "APIRequest",
//                    "Success fetching weather for $cityName"
//                )
            } catch (e: Exception) {
//                Log.e(
//                    "APIRequest",
//                    "Error fetching weather data for $cityName Lat: $lat, Lon: $lon",
//                    e
//                )
                getNetWorkRequestCity(
                    createErrorCity(cityName)
                )
            }
        }
    }

    private fun createErrorCity(cityName: String): City {
        return City(
            cityName = cityName,
            currentCondition = WeatherDay(
                weatherDescription = "",
                date = getDate(_uiState.value.is24Hour),
                temperature = 0,
                weatherIcon = ""
            ),
            forecast7day = listOError7days(),
            forecast24hour = listOfError24Hours(),
            networkRequest = WeatherNetwork.Error,
            lat = 0.0,
            lon = 0.0
        )
    }

    // gets the current date, shows hour in either 24-hour or 12-hour time
    // Tuesday, 07 January 2025 | 17:00
    // Tuesday, 07 January 2025 | 5:00 PM
    private fun getDate(is24Hour: Boolean): String {

        if (is24Hour) {
            return SimpleDateFormat(
                "EEEE, dd MMMM yyyy | HH:00",
                Locale.getDefault()
            ).format(Date())
        } else {
            val date = SimpleDateFormat("EEEE, dd MMMM yyyy | ", Locale.getDefault()).format(Date())
            val hour = SimpleDateFormat("HH", Locale.getDefault()).format(Date())
            val hourIndex = hourArray24.indexOf(hour)

            return "$date ${hourArray12[hourIndex]}"
        }
    }

    private fun createWeatherDay(current: Current): WeatherDay {
        return WeatherDay(
            weatherDescription = current.weather[0].description,
            temperature = round(current.temp).toInt(),
            weatherIcon = createWeatherIconURL(current.weather[0].icon),
            date = getDate(_uiState.value.is24Hour)
        )
    }

    @VisibleForTesting
    internal fun create7DayForecast(listDaily: List<Daily>): List<WeatherDay> {
        val weekday = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())

        var weekDayIndex = weekDayArray.indexOf(weekday)

        val listWeatherDay = mutableListOf<WeatherDay>()

        listWeatherDay.add(
            WeatherDay(
                temperature = round(listDaily[0].temp.day).toInt(),
                weatherDescription = listDaily[0].weather[0].description,
                weatherIcon = createWeatherIconURL(listDaily[0].weather[0].icon),
                date = "Today"
            )
        )
        weekDayIndex++

        for (i in 1..6) {
            listWeatherDay.add(
                WeatherDay(
                    temperature = round(listDaily[i].temp.day).toInt(),
                    weatherDescription = listDaily[i].weather[0].description,
                    weatherIcon = createWeatherIconURL(listDaily[i].weather[0].icon),
                    date = weekDayArray[weekDayIndex++ % weekDayArray.size]
                )
            )
        }
        return listWeatherDay
    }

    // test
    // gets the index of the current hour in the hour array
    // returns the array of either 24-hour format or 12-hour format
    @VisibleForTesting
    internal fun getHourIndexAndHourArray(
        is24Hour: Boolean,
        hourIn24HourTime: String
    ): Pair<Int, Array<String>> {
        //val hour = SimpleDateFormat("HH", Locale.getDefault()).format(Date())

        val hourIndex = hourArray24.indexOf(hourIn24HourTime)

        val stringHour: Array<String> = if (is24Hour) {
            hourArray24
        } else {
            hourArray12
        }
        return Pair(hourIndex, stringHour)
    }

    // test
    private fun create24HourForecast(listHourly: List<Hourly>): List<WeatherHour> {
        val (hourIndexTemp, stringHour) = getHourIndexAndHourArray(
            is24Hour = _uiState.value.is24Hour,
            hourIn24HourTime = SimpleDateFormat("HH", Locale.getDefault()).format(Date())
        )

        var hourIndex = hourIndexTemp

        val listWeatherHour = mutableListOf<WeatherHour>()

        listWeatherHour.add(
            WeatherHour(
                temperature = round(listHourly[0].temp).toInt(),
                weatherIcon = createWeatherIconURL(listHourly[0].weather[0].icon),
                hour = "Now",
                weatherDescription = listHourly[0].weather[0].description
            )
        )
        hourIndex++

        for (i in 1..23) {
            listWeatherHour.add(
                WeatherHour(
                    temperature = round(listHourly[i].temp).toInt(),
                    weatherIcon = createWeatherIconURL(listHourly[i].weather[0].icon),
                    hour = stringHour[hourIndex++ % stringHour.size],
                    weatherDescription = listHourly[i].weather[0].description
                )
            )
        }
        return listWeatherHour
    }

    // test
    @VisibleForTesting
    internal fun change24Hour(listHourly: List<WeatherHour>, is24Hour: Boolean): List<WeatherHour> {
        val (hourIndexTemp, stringHour) = getHourIndexAndHourArray(
            is24Hour = is24Hour,
            hourIn24HourTime = SimpleDateFormat("HH", Locale.getDefault()).format(Date())
        )

        var hourIndex = hourIndexTemp

        val listWeatherHour = mutableListOf<WeatherHour>()

        listWeatherHour.add(listHourly[0])
        hourIndex++

        for (i in 1..23) {
            listWeatherHour.add(
                listHourly[i].copy(hour = stringHour[hourIndex++ % stringHour.size])
            )
        }
        return listWeatherHour
    }

    fun getSearchCityWeather(city: String) {

        _uiState.update {
            it.copy(
                currentSelectedCity = it.currentSelectedCity.copy(networkRequest = WeatherNetwork.Loading),
                currentScreen = Tab.CITYDETAIL,
            )
        }

        getNewWeatherForCity(city) { netWorkRequestCity ->
            _uiState.update {
                it.copy(
                    currentSelectedCity = netWorkRequestCity,
                    isPreviousSavedScreen = false
                )
            }
        }
    }

    fun getHomeCityWeather() {
        _uiState.update {
            it.copy(
                homeCity = it.homeCity.copy(networkRequest = WeatherNetwork.Loading)
            )
        }

        getNewWeatherForCity(_uiState.value.homeCity.cityName) { netWorkRequestCity ->
            _uiState.update { it.copy(homeCity = netWorkRequestCity) }
        }
    }

    fun getSavedCitiesWeather() {

        val updatedCities = _uiState.value.listOfSavedCitiesOrderAdded.map { city ->
            city.copy(networkRequest = WeatherNetwork.Loading)
        }
        _uiState.update { it.copy(listOfSavedCitiesOrderAdded = updatedCities) }

        _uiState.value.listOfSavedCitiesOrderAdded.forEachIndexed { index, city ->
            getNewWeatherForCity(city.cityName) { netWorkRequestCity ->
                _uiState.update { uiState ->
                    val updatedCities = uiState.listOfSavedCitiesOrderAdded.toMutableList()
                    updatedCities[index] = netWorkRequestCity
                    uiState.copy(listOfSavedCitiesOrderAdded = updatedCities) // creating new list
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[APPLICATION_KEY] as WeatherAppApplication) // used to find the app's WeatherAppApplication object
                val weatherRepository = application.container.weatherRepository
                WeatherAppViewModel(weatherRepository = weatherRepository)
            }
        }
    }
}