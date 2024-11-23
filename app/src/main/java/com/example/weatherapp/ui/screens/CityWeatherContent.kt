package com.example.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.data.homeCityOttawa
import com.example.weatherapp.data.stringToDrawableRes
import com.example.weatherapp.model.City
import com.example.weatherapp.model.WeatherDay
import com.example.weatherapp.model.WeatherHour
import com.example.weatherapp.model.WeatherNetwork

@Composable
fun CityWeatherContent(city: City, is24Hour: Boolean, modifier: Modifier = Modifier) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
    ) {
        when (city.networkRequest) {
            WeatherNetwork.Error -> {
                Text(text = "")

                Image(
                    painter = painterResource(R.drawable.network_error),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )

                Text(text = "")
                Text(text = "")
            }

            WeatherNetwork.Loading -> {
                Text(text = "")

                Image(
                    painter = painterResource(R.drawable.network_loading),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )

                Text(text = "")
                Text(text = "")
            }

            WeatherNetwork.Success -> {
                Text(text = city.currentCondition.weatherDescription)

                Image(
                    painter = painterResource(stringToDrawableRes(city.currentCondition.weatherIcon)),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )

                Text(text = "${city.currentCondition.temperature} °")
                Text(city.currentCondition.date)
            }
        }

        hourRow(city.forecast24hour, networkStatus = city.networkRequest, is24Hour = is24Hour)
        forecast7day(city.forecast7day, city.networkRequest)
    }
}

@Composable
fun hourRow(
    forecast24Hour: List<WeatherHour>,
    is24Hour: Boolean,
    networkStatus: WeatherNetwork,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(forecast24Hour) {
            hourCard(
                weatherHour = it,
                modifier = Modifier.padding(5.dp),
                is24Hour = is24Hour,
                networkStatus = networkStatus
            )
        }
    }
}

@Composable
fun forecast7day(
    forecast7day: List<WeatherDay>,
    networkStatus: WeatherNetwork,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(forecast7day) {
            SevenDayCard(
                weatherday = it,
                networkStatus = networkStatus,
                modifier = Modifier.padding(10.dp),
            )
        }
    }
}

@Composable
fun SevenDayCard(
    weatherday: WeatherDay,
    networkStatus: WeatherNetwork,
    modifier: Modifier = Modifier
) {
    when (networkStatus) {
        WeatherNetwork.Error -> {
            Box {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        weatherday.date,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "",
                        fontSize = 26.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(150.dp))
                    Image(
                        painter = painterResource(id = R.drawable.network_error),
                        contentDescription = weatherday.weatherDescription,
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        "",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .weight(1f)
                    )

                }
            }
        }

        WeatherNetwork.Loading -> {
            Box {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        weatherday.date,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "",
                        fontSize = 26.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(150.dp))
                    Image(
                        painter = painterResource(id = R.drawable.network_loading),
                        contentDescription = weatherday.weatherDescription,
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        "",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .weight(1f)
                    )

                }
            }
        }

        WeatherNetwork.Success -> {
            Box {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        weatherday.date,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )


                    Text(
                        text = "${weatherday.temperature}°",
                        fontSize = 26.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(150.dp))
                    Image(
                        painter = painterResource(id = stringToDrawableRes(weatherday.weatherIcon)),
                        contentDescription = weatherday.weatherDescription,
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        weatherday.weatherDescription,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .weight(1f)
                    )

                }
            }
        }
    }
}


@Composable
fun hourCard(
    weatherHour: WeatherHour,
    networkStatus: WeatherNetwork,
    is24Hour: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(65.dp)
            .height(90.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (networkStatus) {
                WeatherNetwork.Error -> {
                    Text("")
                    Image(
                        painter = painterResource(id = R.drawable.network_error),
                        contentDescription = "Error",
                        modifier = Modifier.size(30.dp)
                    )
                    Text("")

                }

                WeatherNetwork.Loading -> {
                    Text("")
                    Image(
                        painter = painterResource(id = R.drawable.network_loading),
                        contentDescription = "Error",
                        modifier = Modifier.size(30.dp)
                    )
                    Text("")
                }

                WeatherNetwork.Success -> {
                    Row {
                        val hourText = if (weatherHour.hour == "Now" || !is24Hour){
                            weatherHour.hour
                        } else {
                            "${weatherHour.hour}:00"
                        }

                        Text(
                            hourText,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(end = 2.dp)
                        )
                    }
                    Image(
                        painter = painterResource(id = stringToDrawableRes(weatherHour.weatherIcon)),
                        contentDescription = weatherHour.weatherDescription,
                        modifier = Modifier.size(30.dp)
                    )

                    Row {
                        Text(weatherHour.temperature.toString(), fontSize = 12.sp)
                        Text(stringResource(id = R.string.degree), fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CityWeatherLoadingPreview() {
    CityWeatherContent(homeCityOttawa.copy(networkRequest = WeatherNetwork.Loading), is24Hour = true)
}

@Preview(showSystemUi = true)
@Composable
fun CityWeatherErrorPreview() {
    CityWeatherContent(homeCityOttawa.copy(networkRequest = WeatherNetwork.Error), is24Hour = true)
}

@Preview(showSystemUi = true)
@Composable
fun CityWeatherSuccessPreview() {
    CityWeatherContent(homeCityOttawa.copy(networkRequest = WeatherNetwork.Success), is24Hour = true)
}