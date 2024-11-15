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
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun searchCitiesContent(
    listOfCities: List<City>,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onCityClick: (City) -> Unit,
    onSearchEnter: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SearchBar(
            searchValue = searchValue,
            onSearchValueChange = onSearchValueChange,
            onSearchEnter = onSearchEnter
        )
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
            onSearch = { onSearchEnter(searchValue)},
            onDone = {onSearchEnter(searchValue)}
        ),
        modifier = modifier.fillMaxWidth()
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
            when (it.networkRequest) {
                WeatherNetwork.Error ->
                    cityCardError(it, modifier = Modifier
                    .padding(10.dp))

                WeatherNetwork.Loading ->
                    cityCardLoading(it, modifier = Modifier
                    .padding(10.dp))

                is WeatherNetwork.Success ->
                    cityCardSuccess(it, modifier = Modifier
                    .padding(10.dp)
                    .clickable { onCityClick(it) })
            }
        }
    }
}

@Composable
fun cityCardSuccess(city: City, modifier: Modifier = Modifier) {

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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = city.currentCondition.condition.drawableResId),
                contentDescription = city.currentCondition.condition.weatherDescription.joinToString(),
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
                Text(city.currentCondition.condition.weatherDescription.joinToString())
            }

            Row(modifier = Modifier.padding(16.dp)) {
                Text(text = city.currentCondition.temperature.toString(), fontSize = 30.sp)
                Text(stringResource(id = R.string.degree), fontSize = 25.sp)
            }
        }
    }
}

@Composable
fun cityCardLoading(city: City, modifier: Modifier = Modifier) {
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.loading_img),
                contentDescription = city.currentCondition.condition.weatherDescription.joinToString(),
                modifier = Modifier
                    .size(200.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun cityCardError(city: City, modifier: Modifier = Modifier) {

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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.error_img),
                contentDescription = city.currentCondition.condition.weatherDescription.joinToString(),
                modifier = Modifier
                    .size(70.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun savedScreenPreview() {
    searchCitiesContent(
        listOfCities,
        onCityClick = {},
        onSearchEnter = {},
        onSearchValueChange = {},
        searchValue = "New York"
    )
}

@Preview(showSystemUi = true)
@Composable
fun cityCardPreview() {
    cityCardSuccess(city = listOfCities[0])
}

@Preview(showSystemUi = true)
@Composable
fun cityCardLoadingPreview() {
    cityCardLoading(city = listOfCities[0])
}

@Preview(showSystemUi = true)
@Composable
fun cityCardErrorPreview() {
    cityCardError(city = listOfCities[0])
}