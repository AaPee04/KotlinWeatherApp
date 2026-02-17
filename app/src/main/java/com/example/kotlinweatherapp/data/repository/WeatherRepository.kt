package com.example.kotlinweatherapp.data.repository

import com.example.kotlinweatherapp.BuildConfig
import com.example.kotlinweatherapp.data.local.SearchHistoryDao
import com.example.kotlinweatherapp.data.local.WeatherDao
import com.example.kotlinweatherapp.data.model.SearchHistoryEntity
import com.example.kotlinweatherapp.data.model.WeatherEntity
import com.example.kotlinweatherapp.data.model.WeatherResponse
import com.example.kotlinweatherapp.data.remote.RetrofitClient
import com.example.kotlinweatherapp.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.HttpException
import java.io.IOException

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