package com.example.weatherapp

import com.example.weatherapp.ui.WeatherAppViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class WeatherAppTests {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Set Main dispatcher for testing
    }

    @Test
    fun getCurrentHourIndexAnd24HourTimeArray_12AM(){
        val viewModel = WeatherAppViewModel(weatherRepository = FakeWeatherRepository())

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
        val viewModel = WeatherAppViewModel(weatherRepository = FakeWeatherRepository())

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