package com.example.kotlinweatherapp.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// 📁 data/remote/RetrofitClient.kt
// Singleton-objekti: luodaan vain kerran ja käytetään koko sovelluksessa.
// "by lazy" tarkoittaa, että instanssi luodaan vasta kun sitä ensimmäisen kerran tarvitaan.

object RetrofitClient {

    // OpenWeatherMap API:n perusosoite
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    // OkHttpClient hoitaa HTTP-yhteydet
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            // BODY = logittaa koko pyynnön ja vastauksen (kehityksessä hyödyllinen)
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)  // Max 30s yhteyden muodostamiseen
        .readTimeout(30, TimeUnit.SECONDS)      // Max 30s vastauksen lukemiseen
        .build()

    // weatherApiService luodaan lazyna → vain yksi instanssi koko sovelluksessa
    val weatherApiService: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)                              // API:n perusosoite
            .client(okHttpClient)                           // Käytetään yllä luotua HTTP-clientia
            .addConverterFactory(GsonConverterFactory.create())  // JSON → Kotlin-objektit
            .build()
            .create(WeatherApiService::class.java)          // Luo rajapinnan toteutus
    }
}