package com.example.weatherapp.ui.screens


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ui.WeatherAppViewModel

@Composable
fun WeatherApp( modifier: Modifier = Modifier){
    val weatherAppViewModel: WeatherAppViewModel = viewModel(factory = WeatherAppViewModel.Factory)

    val weatherUIState by weatherAppViewModel.uiState.collectAsState()

    BaseScreen(
        weatherUIState = weatherUIState,
        onCityClick = weatherAppViewModel::clickCity,
        onTabPressed = weatherAppViewModel::tabClick,
        onSearchScreenBack = weatherAppViewModel::backPressClickSearchScreen,
        onSearchSavedBack = weatherAppViewModel::backPressClickSavedScreen,
        onSearchEnterNetwork = weatherAppViewModel::getSearchCityWeather,
        onSearchNetworkValueChange = weatherAppViewModel::onSearchNetwork,
        searchNetworkValue = weatherUIState.userSearchNetwork,
        onSavedSearchEnter = {},
        onSavedSearchValueChange = weatherAppViewModel::onSearchSaved,
        savedScreenSearchValue = weatherUIState.userSearchSaved,
        addToSavedCities = weatherAppViewModel::addCity,
        isCityInSavedList = weatherAppViewModel::isCityInSavedList,
        onDeleteButtonClick = weatherAppViewModel::deleteCity,
        onRefreshSavedButtonClick = weatherAppViewModel::getSavedCitiesWeather,
        onRefreshHomeButtonClick = weatherAppViewModel::getHomeCityWeather,
        onTimeCardClick = weatherAppViewModel::onTimeCardClick,
        modifier = modifier
    )
}
