package com.example.weatherapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.weatherapp.ui.screens.WeatherApp
import com.example.weatherapp.ui.theme.WeatherAppTheme
import org.junit.Rule
import org.junit.Test

class WeatherAppUITests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun hourButtonChange() {

        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherApp()
            }
        }
        composeTestRule.onNodeWithText("12Hr").performClick()
        composeTestRule.onNodeWithText("24Hr").assertExists()
    }
}