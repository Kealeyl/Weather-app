package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.weatherapp.ui.screens.WeatherApp
import com.example.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                WeatherAppTheme {
                    Scaffold{ padding ->

                        WeatherApp(modifier = Modifier.padding(padding))
                    }

//                    val layoutDirection = LocalLayoutDirection.current
//                    Surface(
//                        modifier = Modifier
//                            .padding(
//                                WindowInsets.safeDrawing.asPaddingValues()
//                                    .calculateStartPadding(layoutDirection)
//                            )
//                    ) {
//                        WeatherApp()
//                    }
                }
            }
        }
    }
}



