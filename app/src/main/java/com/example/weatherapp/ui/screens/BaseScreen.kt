package com.example.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.data.Tab
import com.example.weatherapp.data.WeatherUIState
import com.example.weatherapp.data.homeCityOttawa
import com.example.weatherapp.model.City
import com.example.weatherapp.model.WeatherNetwork

@Composable
fun BaseScreen(
    weatherUIState: WeatherUIState,
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


    when (weatherUIState.currentScreen) {
        Tab.HOME -> HomeCityWeatherScreen(
            weatherUIState = weatherUIState,
            onRefreshButtonClick = onRefreshHomeButtonClick,
            onTimeCardClick = onTimeCardClick,
            modifier = modifier
        )

        Tab.SEARCH -> SearchCitiesScreen(
            onSearchEnter = onSearchEnterNetwork,
            onSearchValueChange = onSearchNetworkValueChange,
            searchValue = searchNetworkValue,
            modifier = modifier
        )

        Tab.SAVED -> SavedCitiesScreen( // back press
            listOfCities = weatherUIState.listOfSavedCitiesOrderAdded,
            onCityClick = onCityClick, // city weather screen
            onSearchEnter = onSavedSearchEnter,
            onSearchValueChange = onSavedSearchValueChange,
            searchValue = savedScreenSearchValue,
            onRefreshButtonClick = onRefreshSavedButtonClick,
            modifier = modifier
        )

        Tab.CITYDETAIL ->
            if (weatherUIState.isPreviousSavedScreen) {
                ClickedCityWeatherScreen(
                    onBackButtonClicked = onSearchSavedBack, // saved screen
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
    }
}

@Composable
private fun SearchCitiesScreen(
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onSearchEnter: (String) -> Unit,
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
    }
}

@Composable
private fun SavedCitiesScreen(
    listOfCities: List<City>,
    onCityClick: (City) -> Unit,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onRefreshButtonClick: () -> Unit,
    onSearchEnter: (String) -> Unit,
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
    }
}






@Preview(showSystemUi = true)
@Composable
fun BaseScreenHomePreview() {
    BaseScreen(
        onCityClick = {},
        weatherUIState =
        WeatherUIState(is24Hour = false, homeCity = homeCityOttawa.copy(networkRequest = WeatherNetwork.Success)),
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