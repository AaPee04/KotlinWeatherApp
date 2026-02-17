package com.example.kotlinweatherapp.data.repository

import com.example.kotlinweatherapp.BuildConfig
import com.example.kotlinweatherapp.data.local.dao.SearchHistoryDao
import com.example.kotlinweatherapp.data.local.dao.WeatherDao
import com.example.kotlinweatherapp.data.local.entity.SearchHistoryEntity
import com.example.kotlinweatherapp.data.local.entity.WeatherEntity
import com.example.kotlinweatherapp.data.remote.RetrofitClient
import kotlinx.coroutines.flow.Flow

class WeatherRepository(
    private val weatherDao: WeatherDao,
    private val historyDao: SearchHistoryDao
) {

    private val apiService = RetrofitClient.weatherApiService
    private val apiKey = BuildConfig.OPEN_WEATHER_API_KEY

    fun getWeatherFromDb(city: String): Flow<WeatherEntity?> {
        return weatherDao.getWeather(city)
    }

    fun getSearchHistory(): Flow<List<SearchHistoryEntity>> {
        return historyDao.getSearchHistory()
    }

    suspend fun fetchWeatherIfNeeded(city: String) {

        val cachedWeather = weatherDao.getWeatherOnce(city)

        val isExpired = cachedWeather == null ||
                System.currentTimeMillis() - cachedWeather.lastUpdated > 30 * 60 * 1000

        if (!isExpired) return

        val response = apiService.getWeather(city, apiKey)

        val entity = WeatherEntity(
            city = response.name,
            country = response.sys.country,
            temperature = response.main.temp,
            description = response.weather[0].description,
            icon = response.weather[0].icon,
            feelsLike = response.main.feels_like,
            tempMin = response.main.tempMin,
            tempMax = response.main.tempMax,
            humidity = response.main.humidity,
            windSpeed = response.wind.speed,
            pressure = response.main.pressure,
            lastUpdated = System.currentTimeMillis()
        )

        weatherDao.insertWeather(entity)

        historyDao.insertSearch(
            SearchHistoryEntity(
                city = city,
                searchedAt = System.currentTimeMillis()
            )
        )
    }
}