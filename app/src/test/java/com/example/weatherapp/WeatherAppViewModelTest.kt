package com.example.weatherapp


import com.example.weatherapp.fake.FakeDataSource
import com.example.weatherapp.fake.FakeNetworkWeatherRepository
import com.example.weatherapp.model.WeatherNetwork
import com.example.weatherapp.rules.TestDispatcherRule
import com.example.weatherapp.ui.WeatherAppViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class WeatherAppViewModelTest {

    // Main dispatcher wraps the Android UI thread, which is not available in Unit test
    // for when view model calls viewModelScope.launch
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun weatherAppViewModel_getCityWeather_verifyWeatherUIStateSuccessForHomeCity() =
        runTest{
            val viewModel = WeatherAppViewModel(weatherRepository = FakeNetworkWeatherRepository())
            assertEquals(WeatherNetwork.Success, viewModel.uiState.value.homeCity.networkRequest)
        }


    @Test
    fun networkWeatherRepository_getLatLongName_verifyHomeCityLatLonName() =
        runTest {
            val viewModel = WeatherAppViewModel(weatherRepository = FakeNetworkWeatherRepository())

            assertEquals(FakeDataSource.geocoderRequestOttawa[0].lat, viewModel.uiState.value.homeCity.lat)
            assertEquals(FakeDataSource.geocoderRequestOttawa[0].lon, viewModel.uiState.value.homeCity.lon)
            assertEquals(FakeDataSource.geocoderRequestOttawa[0].name, viewModel.uiState.value.homeCity.cityName)
        }

    @Test
    fun networkWeatherRepository_getCityRequest_verifyHomeCity7dayForecast() =
        runTest {
            val viewModel = WeatherAppViewModel(weatherRepository = FakeNetworkWeatherRepository())
            assertEquals(viewModel.create7DayForecast(FakeDataSource.cityRequestOttawa.daily), viewModel.uiState.value.homeCity.forecast7day)
        }

    @Test
    fun getCurrentHourIndexAnd24HourTimeArray_12AM(){

        val viewModel = WeatherAppViewModel(weatherRepository = FakeNetworkWeatherRepository())

        // expected values
        val expectedArray = arrayOf(
            "00", "01", "02", "03", "04", "05", "06", "07",
            "08", "09", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23"
        )

        val actualValues = viewModel.getHourIndexAndHourArray(is24Hour = true, "00")

        assertTrue(expectedArray.contentEquals(actualValues.second))
        assertEquals(0, actualValues.first)
    }

    @Test
    fun getCurrentHourIndexAnd12HourTimeArray_12AM(){
        val viewModel = WeatherAppViewModel(weatherRepository = FakeNetworkWeatherRepository())

        // expected values
        val expectedArray = arrayOf(
            "12:00 AM", "1:00 AM", "2:00 AM", "3:00 AM", "4:00 AM",
            "5:00 AM", "6:00 AM", "7:00 AM", "8:00 AM", "9:00 AM",
            "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM",
            "3:00 PM", "4:00 PM", "5:00 PM", "6:00 PM", "7:00 PM",
            "8:00 PM", "9:00 PM", "10:00 PM", "11:00 PM"
        )

        val actualValues = viewModel.getHourIndexAndHourArray(is24Hour = false, "00")

        assertTrue(expectedArray.contentEquals(actualValues.second))
        assertEquals(0, actualValues.first)
    }
}