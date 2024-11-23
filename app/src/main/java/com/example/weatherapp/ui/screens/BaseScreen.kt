package com.example.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.Tab
import com.example.weatherapp.data.WeatherUIState
import com.example.weatherapp.data.homeCityOttawa
import com.example.weatherapp.model.City
import com.example.weatherapp.model.WeatherNetwork

@Composable
fun BaseScreen(
    weatherUIState: WeatherUIState,
    onTabPressed: (Tab) -> Unit,
    onCityClick: (City) -> Unit,
    onSearchScreenBack: () -> Unit,
    onSearchSavedBack: () -> Unit,
    searchNetworkValue: String,
    onSearchNetworkValueChange: (String) -> Unit,
    onSearchEnterNetwork: (String) -> Unit,
    savedScreenSearchValue: String,
    onSavedSearchValueChange: (String) -> Unit,
    onSavedSearchEnter: (String) -> Unit,
    addToSavedCities: (City) -> Unit,
    isCityInSavedList: (City) -> Boolean,
    onDeleteButtonClick: (City) -> Unit,
    onRefreshSavedButtonClick: () -> Unit,
    onRefreshHomeButtonClick: () -> Unit,
    onTimeCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val navigationItemContentList = listOf(
        NavigationItemContent(
            Icons.Default.Home,
            "Home screen ${weatherUIState.homeCity.cityName}",
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
            onRefreshButtonClick = onRefreshHomeButtonClick,
            onTimeCardClick = onTimeCardClick,
            modifier = modifier
        )

        Tab.SEARCH -> SearchCitiesScreen(
            weatherUIState = weatherUIState,
            navigationItemContentList = navigationItemContentList,
            onTabPressed = onTabPressed,
            onSearchEnter = onSearchEnterNetwork,
            onSearchValueChange = onSearchNetworkValueChange,
            searchValue = searchNetworkValue,
            modifier = modifier
        )

        Tab.SAVED -> SavedCitiesScreen( // back press
            listOfCities = weatherUIState.listOfSavedCitiesOrderAdded,
            onCityClick = onCityClick, // city weather screen
            weatherUIState = weatherUIState,
            navigationItemContentList = navigationItemContentList,
            onTabPressed = onTabPressed,
            onSearchEnter = onSavedSearchEnter,
            onSearchValueChange = onSavedSearchValueChange,
            searchValue = savedScreenSearchValue,
            onRefreshButtonClick = onRefreshSavedButtonClick,
            modifier = modifier
        )

        Tab.CITYDETAIL ->
            if (weatherUIState.isPreviousSavedScreen) {
                ClickedCityWeatherScreen(
                    onBackButtonClicked = onSearchSavedBack,
                    weatherUIState = weatherUIState,
                    onAddButtonClick = addToSavedCities,
                    isCityInSavedList = isCityInSavedList,
                    onDeleteButtonClick = onDeleteButtonClick,
                    modifier = modifier
                )
            } else {
                ClickedCityWeatherScreen(
                    onBackButtonClicked = onSearchScreenBack,
                    weatherUIState = weatherUIState,
                    onAddButtonClick = addToSavedCities,
                    isCityInSavedList = isCityInSavedList,
                    onDeleteButtonClick = onDeleteButtonClick,
                    modifier = modifier
                )
            }
    }
}

@Composable
private fun ClickedCityWeatherScreen(
    weatherUIState: WeatherUIState,
    onBackButtonClicked: () -> Unit,
    onAddButtonClick: (City) -> Unit,
    isCityInSavedList: (City) -> Boolean,
    onDeleteButtonClick: (City) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        CityDetailsScreenTopBar(
            weatherUIState = weatherUIState,
            onBackButtonClicked = onBackButtonClicked,
            onAddButtonClick = onAddButtonClick,
            isCityInSavedList = isCityInSavedList,
            onDeleteButtonClick = onDeleteButtonClick
        )
        CityWeatherContent(weatherUIState.currentSelectedCity, modifier = Modifier.weight(1f), is24Hour = weatherUIState.is24Hour)
    }
}

@Composable
private fun HomeCityWeatherScreen(
    weatherUIState: WeatherUIState,
    onTabPressed: (Tab) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    onTimeCardClick: () -> Unit,
    onRefreshButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        HomeScreenTopBar(
            weatherUIState,
            onTimeCardClick = onTimeCardClick,
            onRefreshButtonClick = onRefreshButtonClick,
            is24Hour = weatherUIState.is24Hour,
            modifier = Modifier.background(Color.LightGray)
        )
        CityWeatherContent(weatherUIState.homeCity, modifier = Modifier.weight(1f), is24Hour = weatherUIState.is24Hour)
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
    onTabPressed: (Tab) -> Unit,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onSearchEnter: (String) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            SearchBar(
                searchValue = searchValue,
                onSearchValueChange = onSearchValueChange,
                onSearchEnter = onSearchEnter,
                modifier = Modifier.fillMaxWidth()
            )
        }

        BottomNavigationBar(
            currentTab = weatherUIState.currentScreen,
            onTabPressed = onTabPressed,
            navigationItemContentList = navigationItemContentList
        )
    }
}

@Composable
private fun SavedCitiesScreen(
    weatherUIState: WeatherUIState,
    listOfCities: List<City>,
    onTabPressed: (Tab) -> Unit,
    onCityClick: (City) -> Unit,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onRefreshButtonClick: () -> Unit,
    onSearchEnter: (String) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        savedCitiesContent(
            listOfCities = listOfCities,
            onCityClick = onCityClick,
            searchValue = searchValue,
            onSearchValueChange = onSearchValueChange,
            onSearchEnter = onSearchEnter,
            onRefreshButtonClick = onRefreshButtonClick,
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
        weatherUIState =
        WeatherUIState(is24Hour = false, homeCity = homeCityOttawa.copy(networkRequest = WeatherNetwork.Success)),
        onTabPressed = {},
        onSearchScreenBack = {},
        onSearchSavedBack = {},
        onSearchEnterNetwork = {},
        searchNetworkValue = "",
        onSavedSearchEnter = {},
        onSavedSearchValueChange = {},
        addToSavedCities = {},
        onSearchNetworkValueChange = {},
        isCityInSavedList = {_-> true},
        onDeleteButtonClick = {},
        onRefreshSavedButtonClick = {},
        onRefreshHomeButtonClick = {},
        onTimeCardClick = {},
        savedScreenSearchValue = ""
    )
}

@Preview(showSystemUi = true)
@Composable
fun BaseScreenSavedPreview() {
    BaseScreen(
        onCityClick = {},
        weatherUIState = WeatherUIState(currentScreen = Tab.SAVED),
        onTabPressed = {},
        onSearchScreenBack = {},
        onSearchSavedBack = {},
        onSearchEnterNetwork = {},
        onSearchNetworkValueChange = {},
        onSavedSearchEnter = {},
        onSavedSearchValueChange = {},
        addToSavedCities = {},
        searchNetworkValue = "",
        savedScreenSearchValue = "",
        isCityInSavedList = {_-> true},
        onRefreshSavedButtonClick = {},
        onRefreshHomeButtonClick = {},
        onTimeCardClick = {},
        onDeleteButtonClick = {},
    )
}

@Preview(showSystemUi = true)
@Composable
fun BaseScreenSearchPreview() {
    BaseScreen(
        onCityClick = {},
        weatherUIState = WeatherUIState(currentScreen = Tab.SEARCH),
        onTabPressed = {},
        onSearchScreenBack = {},
        onSearchSavedBack = {},
        onSearchEnterNetwork = {},
        onSearchNetworkValueChange = {},
        onSavedSearchEnter = {},
        onSavedSearchValueChange = {},
        addToSavedCities = {},
        searchNetworkValue = "",
        savedScreenSearchValue = "",
        isCityInSavedList = {_-> true},
        onRefreshSavedButtonClick = {},
        onRefreshHomeButtonClick = {},
        onTimeCardClick = {},
        onDeleteButtonClick = {},
    )
}