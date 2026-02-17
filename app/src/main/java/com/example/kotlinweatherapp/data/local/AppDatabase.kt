package com.example.kotlinweatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlinweatherapp.data.local.dao.SearchHistoryDao
import com.example.kotlinweatherapp.data.local.dao.WeatherDao
import com.example.kotlinweatherapp.data.local.entity.WeatherEntity
import com.example.kotlinweatherapp.data.local.entity.SearchHistoryEntity

@Database(
    entities = [
        WeatherEntity::class,
        SearchHistoryEntity::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "weather_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}