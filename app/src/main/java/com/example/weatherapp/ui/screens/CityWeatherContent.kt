package com.example.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.data.WeatherUIState
import com.example.weatherapp.model.City
import com.example.weatherapp.model.WeatherDay
import com.example.weatherapp.model.WeatherValue
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// City name in app bar

// get user c

@Composable
fun CityWeatherContent(weatherUIState: WeatherUIState, modifier: Modifier = Modifier) {
    // remove from UI code
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy | HH:00")
    val formattedDateTime = currentDateTime.format(formatter)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
    ) {

        Text(text = weatherUIState.city.currentCondition.condition.name)

        Image(
            painter = painterResource(weatherUIState.city.currentCondition.condition.drawableResId),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Text(text = "${weatherUIState.city.currentCondition.temperature} °")


        Text(formattedDateTime)

        hourRow(weatherUIState.city)

        // 7 day forecast

        forecast7day(weatherUIState.city.forecast7day)

    }
}

@Composable
fun hourRow(city: City, modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier) {
        items(city.forecast7day[0].forecast24Hour) {
            hourCard(it, modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun forecast7day(forecast7day: Array<WeatherDay>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(forecast7day) {
            SevenDayCard(it)
        }
    }
}

@Composable
fun SevenDayCard(weatherday: WeatherDay, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            weatherday.date,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 4.dp),
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = weatherday.dayCondition.condition.drawableResId),
                contentDescription = weatherday.dayCondition.condition.name,
                modifier = Modifier.size(40.dp)
            )

            Text(
                weatherday.dayCondition.condition.name,
                modifier = Modifier.padding(end = 75.dp, start = 25.dp)
            )

            Text(text = "${weatherday.dayCondition.temperature}°", fontSize = 30.sp)
        }
    }
}


@Composable
fun hourCard(weatherHour: WeatherValue, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .width(55.dp)
            .height(80.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    weatherHour.temperature.toString(),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(end = 2.dp)
                )
                Text(stringResource(id = R.string.AM), fontSize = 12.sp)
            }
            Image(
                painter = painterResource(id = weatherHour.condition.drawableResId),
                contentDescription = weatherHour.condition.name,
                modifier = Modifier.size(30.dp)
            )

            Row {
                Text(weatherHour.temperature.toString(), fontSize = 12.sp)
                Text(stringResource(id = R.string.degree), fontSize = 12.sp)
            }

        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun homeScreenPreview() {
    CityWeatherContent(WeatherUIState())
}