package com.example.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.data.listOfCities
import com.example.weatherapp.model.City
import com.example.weatherapp.model.WeatherNetwork
import android.util.Log
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.IconButton
import androidx.compose.ui.platform.LocalContext
import com.example.weatherapp.data.stringToDrawableRes

@Composable
fun savedCitiesContent(
    listOfCities: List<City>,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onCityClick: (City) -> Unit,
    onSearchEnter: (String) -> Unit,
    onRefreshButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current // for the toast
    Column(modifier = modifier) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            SearchBar(
                searchValue = searchValue,
                onSearchValueChange = onSearchValueChange,
                onSearchEnter = onSearchEnter,
                modifier = Modifier.weight(1f).padding(start = 10.dp)
            )
            IconButton(
                onClick = {onRefreshButtonClick()
                    Toast.makeText(context, "Refreshed", Toast.LENGTH_SHORT).show()}
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        cityCardColumns(listOfCities, onCityClick)
    }
}

@Composable
fun SearchBar(
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onSearchEnter: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchValue,
        leadingIcon = { Icon(painter = painterResource(id = R.drawable.baseline_search_24), null) },
        onValueChange = onSearchValueChange,
        singleLine = true,
        label = { Text(stringResource(R.string.search)) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearchEnter(searchValue) },
            onDone = { onSearchEnter(searchValue) }
        ),
        modifier = modifier
    )
}

@Composable
fun cityCardColumns(
    listOfCities: List<City>,
    onCityClick: (City) -> Unit,
    modifier: Modifier = Modifier
) {

    Log.d("cityCardColumns", "List of cities: $listOfCities")
    LazyColumn(modifier = modifier) {

        items(listOfCities) {
            cityCard(it, modifier = Modifier
                .padding(10.dp)
                .clickable { onCityClick(it) })
        }
    }
}

@Composable
fun cityCard(city: City, modifier: Modifier = Modifier) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, MaterialTheme.colorScheme.scrim),
                        0f,
                        1000f
                    )
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            when (city.networkRequest) {
                // network error
                WeatherNetwork.Error -> {
                    Image(
                        painter = painterResource(id = R.drawable.network_error),
                        contentDescription = "error",
                        modifier = Modifier.size(70.dp)
                    )
                }

                // network loading
                WeatherNetwork.Loading -> {
                    Image(
                        painter = painterResource(id = R.drawable.network_loading),
                        contentDescription = "loading",
                        modifier = Modifier
                            .size(70.dp)
                    )
                }

                // network success
                WeatherNetwork.Success -> {
                    Image(
                        painter = painterResource(id = stringToDrawableRes(city.currentCondition.weatherIcon)),
                        contentDescription = city.currentCondition.weatherDescription,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .size(50.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            city.cityName,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 4.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(city.currentCondition.weatherDescription)
                    }
                    Row(modifier = Modifier.padding(16.dp)) {
                        Text(text = city.currentCondition.temperature.toString(), fontSize = 30.sp)
                        Text(stringResource(id = R.string.degree), fontSize = 25.sp)
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun savedScreenPreview() {
    savedCitiesContent(
        listOfCities,
        onCityClick = {},
        onSearchEnter = {},
        onSearchValueChange = {},
        onRefreshButtonClick = {},
        searchValue = "New York"
    )
}

@Preview(showSystemUi = true)
@Composable
fun cityCardErrorPreview() {
    cityCard(city = listOfCities[0].copy(networkRequest = WeatherNetwork.Error))
}

@Preview(showSystemUi = true)
@Composable
fun cityCardLoadingPreview() {
    cityCard(city = listOfCities[0].copy(networkRequest = WeatherNetwork.Loading))
}

@Preview(showSystemUi = true)
@Composable
fun cityCardPreview() {
    cityCard(city = listOfCities[0].copy(networkRequest = WeatherNetwork.Success))
}