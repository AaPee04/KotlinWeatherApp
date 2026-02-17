package com.example.kotlinweatherapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(

    @PrimaryKey
    val city: String,

    val country: String,
    val temperature: Double,
    val description: String,
    val icon: String,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Int,

    val lastUpdated: Long
)