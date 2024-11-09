package com.example.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.data.Tab
import com.example.weatherapp.data.WeatherUIState
import com.example.weatherapp.model.City

@Composable
fun BaseScreen(
    weatherUIState: WeatherUIState,
    onTabPressed: (Tab) -> Unit,
    onCityClick: (City) -> Unit,
    onSearchScreenBack: () -> Unit,
    onSearchSavedBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    val navigationItemContentList = listOf(
        NavigationItemContent(
            Icons.Default.Home,
            "Home screen ${weatherUIState.city.cityName}",
            Tab.HOME
        ),
        NavigationItemContent(Icons.Default.Search, "Search for city", Tab.SEARCH),
        NavigationItemContent(Icons.Default.Favorite, "Saved cities", Tab.SAVED)
    )

    when (weatherUIState.currentScreen) {
        Tab.HOME -> HomeCityWeatherScreen(
            navigationItemContentList = navigationItemContentList,
            onTabPressed = onTabPressed,
            weatherUIState = weatherUIState,
            modifier = modifier
        )

        Tab.SEARCH -> SearchCitiesScreen(
            listOfCities = emptyList(), // populate with API ?
            onCityClick = onCityClick, // city weather screen
            weatherUIState = weatherUIState,
            navigationItemContentList = navigationItemContentList,
            onTabPressed = onTabPressed,
            modifier = modifier
        )

        Tab.SAVED -> SearchCitiesScreen( // back press
            listOfCities = weatherUIState.listOfSavedCities,
            onCityClick = onCityClick, // city weather screen
            weatherUIState = weatherUIState,
            navigationItemContentList = navigationItemContentList,
            onTabPressed = onTabPressed,
            modifier = modifier
        )

        Tab.CITYDETAIL ->
            if (weatherUIState.isPreviousSavedScreen) {
                ClickedCityWeatherScreen(
                    onBackButtonClicked = onSearchSavedBack,
                    weatherUIState = weatherUIState,
                    modifier = modifier
                )
            } else {
                ClickedCityWeatherScreen(
                    onBackButtonClicked = onSearchScreenBack,
                    weatherUIState = weatherUIState,
                    modifier = modifier
                )
            }
    }
}

@Composable
private fun ClickedCityWeatherScreen(
    weatherUIState: WeatherUIState,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        CityDetailsScreenTopBar(
            weatherUIState = weatherUIState,
            onBackButtonClicked = onBackButtonClicked
        )
        CityWeatherContent(weatherUIState, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun HomeCityWeatherScreen(
    weatherUIState: WeatherUIState,
    onTabPressed: (Tab) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        HomeScreenTopBar(weatherUIState, {}, modifier = Modifier.background(Color.LightGray))
        CityWeatherContent(weatherUIState, modifier = Modifier.weight(1f))
        BottomNavigationBar(
            currentTab = weatherUIState.currentScreen,
            onTabPressed = onTabPressed,
            navigationItemContentList = navigationItemContentList
        )
    }
}

@Composable
private fun SearchCitiesScreen(
    weatherUIState: WeatherUIState,
    listOfCities: List<City>,
    onTabPressed: (Tab) -> Unit,
    onCityClick: (City) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        searchCitiesContent(
            listOfCities = listOfCities,
            onCityClick = onCityClick,
            modifier = Modifier.weight(1f)
        )
        BottomNavigationBar(
            currentTab = weatherUIState.currentScreen,
            onTabPressed = onTabPressed,
            navigationItemContentList = navigationItemContentList
        )
    }
}


@Composable
private fun BottomNavigationBar(
    currentTab: Tab,
    onTabPressed: ((Tab) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        navigationItemContentList.forEach {
            NavigationBarItem(
                selected = currentTab == it.tab,
                onClick = { onTabPressed(it.tab) },
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.text
                    )
                }
            )
        }
    }
}

// for navigation
private data class NavigationItemContent(
    val icon: ImageVector,
    val text: String,
    val tab: Tab
)

@Preview(showSystemUi = true)
@Composable
fun BaseScreenHomePreview() {
    BaseScreen(
        onCityClick = {},
        weatherUIState = WeatherUIState(),
        onTabPressed = {},
        onSearchScreenBack = {},
        onSearchSavedBack = {})
}

@Preview(showSystemUi = true)
@Composable
fun BaseScreenSavedPreview() {
    BaseScreen(
        onCityClick = {},
        weatherUIState = WeatherUIState(currentScreen = Tab.SAVED),
        onTabPressed = {},
        onSearchScreenBack = {},
        onSearchSavedBack = {})
}

@Preview(showSystemUi = true)
@Composable
fun BaseScreenSearchPreview() {
    BaseScreen(
        onCityClick = {},
        weatherUIState = WeatherUIState(currentScreen = Tab.SEARCH),
        onTabPressed = {},
        onSearchScreenBack = {},
        onSearchSavedBack = {})
}