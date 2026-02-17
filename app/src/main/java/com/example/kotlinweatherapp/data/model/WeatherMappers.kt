package com.example.kotlinweatherapp.data.model

import com.example.kotlinweatherapp.data.local.entity.WeatherEntity

fun WeatherEntity.toWeatherResponse(): WeatherResponse {
    return WeatherResponse(
        weather = listOf(
            Weather(
                id = 0,
                main = "",
                description = description,
                icon = icon
            )
        ),
        main = Main(
            temp = temperature,
            feels_like = feelsLike,
            tempMin = tempMin,
            tempMax = tempMax,
            pressure = pressure,
            humidity = humidity
        ),
        wind = Wind(
            speed = windSpeed,
            deg = 0
        ),
        name = city,
        sys = Sys(
            country = country,
            sunrise = 0,
            sunset = 0
        )
    )
}
