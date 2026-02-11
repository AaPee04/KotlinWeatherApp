package com.example.kotlinweatherapp.data.repository

import android.net.http.HttpException
import com.example.kotlinweatherapp.data.model.WeatherResponse
import com.example.kotlinweatherapp.data.remote.RetrofitClient
import java.io.IOException
import com.example.kotlinweatherapp.util.Result

// 📁 data/repository/WeatherRepository.kt
// Repository kapseloi kaiken datan hakemisen.
// ViewModel ei tiedä mistä data tulee (API, tietokanta, välimuisti).

class WeatherRepository {

    // Käytetään RetrofitClientin singleton-instanssia
    private val apiService = RetrofitClient.weatherApiService

    // API-avain - hanki ilmaiseksi: https://openweathermap.org/api
    private val apiKey = "YOUR_API_KEY"

    // suspend = tämä funktio voi "pysähtyä" odottamaan vastausta
    // ilman säikeen blokkausta (Kotlin Coroutines)
    suspend fun getWeather(city: String): Result<WeatherResponse> {
        return try {
            // Kutsutaan API:a - Retrofit hoitaa HTTP-pyynnön
            val response = apiService.getWeather(city, apiKey)
            Result.Success(response)     // Palautetaan onnistunut tulos
        } catch (e: IOException) {
            // Verkkovirhe (ei nettiyhteyttä, timeout)
            Result.Error(Exception("Verkkovirhe: ${e.message}"))
        } catch (e: HttpException) {
            // HTTP-virhe (404 Not Found, 500 Server Error)
            Result.Error(Exception("Palvelinvirhe: ${e.message}"))
        } catch (e: Exception) {
            // Muu virhe
            Result.Error(Exception("Tuntematon virhe: ${e.message}"))
        }
    }
}