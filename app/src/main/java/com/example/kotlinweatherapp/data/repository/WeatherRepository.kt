package com.example.kotlinweatherapp.data.repository

import com.example.kotlinweatherapp.BuildConfig
import com.example.kotlinweatherapp.data.model.WeatherResponse
import com.example.kotlinweatherapp.data.remote.RetrofitClient
import com.example.kotlinweatherapp.util.Result
import retrofit2.HttpException
import java.io.IOException

class WeatherRepository {

    private val apiService = RetrofitClient.weatherApiService

    private val apiKey = BuildConfig.OPEN_WEATHER_API_KEY

    suspend fun getWeather(city: String): Result<WeatherResponse> {

        println("API KEY FROM BUILDCONFIG: $apiKey")

        return try {
            val response = apiService.getWeather(city, apiKey)
            Result.Success(response)
        } catch (e: IOException) {
            Result.Error(Exception("Verkkovirhe: ${e.message}"))
        } catch (e: HttpException) {
            Result.Error(Exception("Palvelinvirhe: ${e.message()}"))
        } catch (e: Exception) {
            Result.Error(Exception("Tuntematon virhe: ${e.message}"))
        }
    }
}
