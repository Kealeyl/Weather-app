package com.example.weatherapp.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.data.Tab
import com.example.weatherapp.data.WeatherUIState

@Composable
fun HomeScreenTopBar(
    weatherUIState: WeatherUIState,
    onGridButtonClick: () -> Unit,
    onRefreshButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onGridButtonClick,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_grid_view_24),
                        contentDescription = ""
                    )
                }
                Text(
                    weatherUIState.homeCity.cityName,
                    modifier = Modifier.align(Alignment.Center)
                )

                IconButton(
                    onClick = onRefreshButtonClick,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }
            }
        }
    }
}

@Composable
fun CityDetailsScreenTopBar(
    onBackButtonClicked: () -> Unit,
    weatherUIState: WeatherUIState,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackButtonClicked()
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = modifier.fillMaxWidth()) {
            IconButton(onClick = onBackButtonClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

            if (weatherUIState.currentScreen == Tab.SEARCH) {
                Text("Search for City", modifier = Modifier.align(Alignment.Center))
            } else {

                Text(
                    weatherUIState.currentSelectedCity.cityName,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ToDoAppBarDetails() {
    CityDetailsScreenTopBar(onBackButtonClicked = {}, weatherUIState = WeatherUIState())
}

@Preview(showSystemUi = true)
@Composable
fun ToDoAppBarHome() {
    HomeScreenTopBar(onGridButtonClick = {}, weatherUIState = WeatherUIState(), onRefreshButtonClick = {})
}

@Preview(showSystemUi = true)
@Composable
fun ToDoAppBarDetailsSearch() {
    CityDetailsScreenTopBar(
        onBackButtonClicked = {},
        weatherUIState = WeatherUIState(currentScreen = Tab.SEARCH)
    )
}
