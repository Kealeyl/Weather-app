package com.example.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.data.listOfCities
import com.example.weatherapp.model.WeatherDay
import com.example.weatherapp.model.WeatherValue

import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun hourCard(weatherHour: WeatherValue, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .width(80.dp)
            .height(120.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    weatherHour.temperature.toString(),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(end = 2.dp)
                )
                Text(stringResource(id = R.string.AM), fontSize = 18.sp)
            }
            Image(
                painter = painterResource(id = weatherHour.condition.drawableResId),
                contentDescription = weatherHour.condition.name,
                modifier = Modifier.size(50.dp)
            )

            Row {
                Text(weatherHour.temperature.toString(), fontSize = 18.sp)
                Text(stringResource(id = R.string.degree), fontSize = 18.sp)
            }

        }
    }
}

@Preview(name = "hour card", showSystemUi = true)
@Composable
private fun hourCardPreview() {
    WeatherAppTheme {
        hourCard(listOfCities[0].forecast7day[0].forecast24Hour[0], Modifier.padding(20.dp))
    }
}