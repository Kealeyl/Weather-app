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
import com.example.weatherapp.model.WeatherDay
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun SevenDayCard(weatherday: WeatherDay, modifier: Modifier = Modifier) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                weatherday.date,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 4.dp),
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(id = weatherday.dayCondition.condition.drawableResId),
                contentDescription = weatherday.dayCondition.condition.name,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .size(50.dp)
            )
            Column(modifier = Modifier.weight(1f)) {

                Text(weatherday.dayCondition.condition.name)
            }

            Row(modifier = Modifier.padding(16.dp)) {
                Text(text = weatherday.dayCondition.temperature.toString(), fontSize = 30.sp)
                Text(stringResource(id = R.string.degree), fontSize = 25.sp)
            }
        }


}

@Preview(showSystemUi = true)
@Composable
private fun sevenDayCardPreview() {
    WeatherAppTheme {
        SevenDayCard(
            listOfCities[0].forecast7day[0],
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
}