package com.example.kotlinweatherapp.data.local

import androidx.room.*
import com.example.kotlinweatherapp.data.model.Weather
import com.example.kotlinweatherapp.data.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE city = :city LIMIT 1")
    fun getWeather(city: String): Flow<WeatherEntity>

    @Query("SELECT * FROM weather WHERE city = :city LIMIT 1")
    suspend fun getWeatherOnce(city: String): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("DELETE FROM weather")
    suspend fun clearAll()

}