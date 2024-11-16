package com.example.weatherapp.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.data.Tab
import com.example.weatherapp.data.WeatherUIState
import com.example.weatherapp.model.City

@Composable
fun HomeScreenTopBar(
    weatherUIState: WeatherUIState,
    onGridButtonClick: () -> Unit,
    onRefreshButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current // for the toast

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
                // refresh
                IconButton(
                    onClick = { onRefreshButtonClick()
                        Toast.makeText(context, "Refreshed", Toast.LENGTH_SHORT).show()},
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
    onAddButtonClick: (City) -> Unit,
    weatherUIState: WeatherUIState,
    onDeleteButtonClick: (City) -> Unit,
    isCityInSavedList: (City) -> Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current // for the toast
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
            Text(
                weatherUIState.currentSelectedCity.cityName,
                modifier = Modifier.align(Alignment.Center)
            )

            if(isCityInSavedList(weatherUIState.currentSelectedCity)){
                Button(
                    onClick = {
                        onDeleteButtonClick(weatherUIState.currentSelectedCity)
                        Toast.makeText(context, "Deleted ${weatherUIState.currentSelectedCity.cityName}", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Text("Delete")
                }

            } else {
                Button(
                    onClick = {
                        onAddButtonClick(weatherUIState.currentSelectedCity)
                        Toast.makeText(context, "Added ${weatherUIState.currentSelectedCity.cityName}", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Text("Add")
                }
            }


        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ToDoAppBarDetails() {
    CityDetailsScreenTopBar(
        onBackButtonClicked = {},
        weatherUIState = WeatherUIState(),
        onAddButtonClick = {},
        isCityInSavedList = {_ -> true},
        onDeleteButtonClick = {})
}

@Preview(showSystemUi = true)
@Composable
fun ToDoAppBarHome() {
    HomeScreenTopBar(
        onGridButtonClick = {},
        weatherUIState = WeatherUIState(),
        onRefreshButtonClick = {})
}
