package com.example.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.example.weatherapp.data.homeCityOttawa
import com.example.weatherapp.model.City
import com.example.weatherapp.model.WeatherDay
import com.example.weatherapp.model.WeatherNetwork
import com.example.weatherapp.model.WeatherValue

@Composable
fun CityWeatherContent(city: City, modifier: Modifier = Modifier) {

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
                Text(text = city.currentCondition.condition.weatherDescription.joinToString())

                Image(
                    painter = painterResource(city.currentCondition.condition.drawableResId),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )

                Text(text = "${city.currentCondition.temperature} °")
                Text(city.currentCondition.time)
            }
        }

        hourRow(city.forecast7day[0].forecast24Hour, city.networkRequest)
        forecast7day(city.forecast7day, city.networkRequest)
    }
}

@Composable
fun hourRow(
    forecast24Hour: Array<WeatherValue>,
    networkStatus: WeatherNetwork,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(forecast24Hour) {
            hourCard(
                weatherHour = it,
                modifier = Modifier.padding(5.dp),
                networkStatus = networkStatus
            )
        }
    }
}

@Composable
fun forecast7day(
    forecast7day: Array<WeatherDay>,
    networkStatus: WeatherNetwork,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(forecast7day) {
            SevenDayCard(weatherday = it, networkStatus = networkStatus)
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

                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "", fontSize = 30.sp)
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.network_error),
                    contentDescription = "Error",
                    modifier = Modifier.size(40.dp).align(Alignment.Center)
                )
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

                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "", fontSize = 30.sp)
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.network_loading),
                    contentDescription = "Error",
                    modifier = Modifier.size(40.dp).align(Alignment.Center)
                )
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

                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            weatherday.dayCondition.condition.weatherDescription.joinToString(),
                            modifier = Modifier.padding(end = 60.dp)
                        )

                        Text(text = "${weatherday.dayCondition.temperature}°", fontSize = 30.sp)
                    }
                }

                Image(
                    painter = painterResource(id = weatherday.dayCondition.condition.drawableResId),
                    contentDescription = weatherday.dayCondition.condition.weatherDescription.joinToString(),
                    modifier = Modifier.size(40.dp).align(Alignment.Center)
                )
            }

        }
    }
}


@Composable
fun hourCard(
    weatherHour: WeatherValue,
    networkStatus: WeatherNetwork,
    modifier: Modifier = Modifier
) {
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
                        Text(
                            weatherHour.temperature.toString(),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(end = 2.dp)
                        )
                        Text(stringResource(id = R.string.AM), fontSize = 12.sp)
                    }
                    Image(
                        painter = painterResource(id = weatherHour.condition.drawableResId),
                        contentDescription = weatherHour.condition.weatherDescription.joinToString(),
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
    CityWeatherContent(homeCityOttawa.copy(networkRequest = WeatherNetwork.Loading))
}

@Preview(showSystemUi = true)
@Composable
fun CityWeatherErrorPreview() {
    CityWeatherContent(homeCityOttawa.copy(networkRequest = WeatherNetwork.Error))
}

@Preview(showSystemUi = true)
@Composable
fun CityWeatherSuccessPreview() {
    CityWeatherContent(homeCityOttawa.copy(networkRequest = WeatherNetwork.Success))
}