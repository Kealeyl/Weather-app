package com.example.weatherapp.ui.screens


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.data.Tab
import com.example.weatherapp.ui.WeatherAppViewModel

// for navigation
private data class NavigationItemContent(
    val icon: ImageVector,
    val text: String,
    val tab: Tab
)

@Composable
fun WeatherApp(modifier: Modifier = Modifier) {
    val weatherAppViewModel: WeatherAppViewModel = viewModel(factory = WeatherAppViewModel.Factory)

    val weatherUIState by weatherAppViewModel.uiState.collectAsState()


    val navigationItemContentList = listOf(
        NavigationItemContent(Icons.Default.Home, "Home screen ${weatherUIState.homeCity.cityName}", Tab.HOME),
        NavigationItemContent(Icons.Default.Search, "Search for city", Tab.SEARCH),
        NavigationItemContent(Icons.Default.Favorite, "Saved cities", Tab.SAVED)
    )

    Scaffold(bottomBar = {

        if(weatherUIState.currentScreen != Tab.CITYDETAIL){
            BottomNavigationBar(
                currentTab = weatherUIState.currentScreen,
                onTabPressed = weatherAppViewModel::tabClick,
                navigationItemContentList = navigationItemContentList
            )
        }


    }) { padding ->

        BaseScreen(
            weatherUIState = weatherUIState,
            onCityClick = weatherAppViewModel::clickCity,
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
            modifier = modifier.padding(padding)
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