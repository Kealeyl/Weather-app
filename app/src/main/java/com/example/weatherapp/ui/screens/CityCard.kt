package com.example.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.data.listOfCities
import com.example.weatherapp.model.City
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun cityCard(city: City, modifier: Modifier = Modifier) {

    Card(
        modifier = modifier
            .width(240.dp)
            .height(70.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = city.currentCondition.condition.drawableResId),
                contentDescription = city.currentCondition.condition.name,
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
                Text(city.currentCondition.condition.name)
            }

            Row(modifier = Modifier.padding(16.dp)) {
                Text(text = city.currentCondition.temperature.toString(), fontSize = 30.sp)
                Text(stringResource(id = R.string.degree), fontSize = 25.sp)
            }
        }
    }

}

@Preview(name = "city card", showSystemUi = true)
@Composable
private fun cityCardPreview() {
    WeatherAppTheme {
        cityCard(
            listOfCities[0],
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
}