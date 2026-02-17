package com.example.kotlinweatherapp.ui.components

import androidx.compose.runtime.Composable
import com.example.kotlinweatherapp.data.model.WeatherEntity
import com.example.kotlinweatherapp.data.model.toWeatherResponse

@Composable
fun WeatherContentFromEntity(weather: WeatherEntity) {

    WeatherContent(
        weather = weather.toWeatherResponse()
    )
}