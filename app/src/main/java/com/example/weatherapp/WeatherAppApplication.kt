package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.data.AppContainer
import com.example.weatherapp.data.DefaultAppContainer

// connect the application object to the application container
// add to Android manifest so app uses the application class
class WeatherAppApplication : Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}