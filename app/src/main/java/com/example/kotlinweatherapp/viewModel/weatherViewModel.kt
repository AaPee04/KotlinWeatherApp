package com.example.kotlinweatherapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinweatherapp.data.local.AppDatabase
import com.example.kotlinweatherapp.data.local.entity.WeatherEntity
import com.example.kotlinweatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException

class WeatherViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = WeatherRepository(
        weatherDao = database.weatherDao(),
        historyDao = database.searchHistoryDao()
    )

    val searchHistory = repository.getSearchHistory()

    private val _searchQuery = MutableStateFlow("Helsinki")

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    val searchQuery = _searchQuery.asStateFlow()


    val weather: StateFlow<WeatherEntity?> =
        _searchQuery
            .flatMapLatest { city ->
                repository.getWeatherFromDb(city)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun searchWeather() {
        viewModelScope.launch {

            _errorMessage.value = null   // nollaa vanha virhe

            try {
                repository.fetchWeatherIfNeeded(_searchQuery.value)
            } catch (e: Exception) {

                if (e is HttpException && e.code() == 404) {
                    _errorMessage.value = "Kaupunkia ei löytynyt"
                } else {
                    _errorMessage.value = "Virhe haettaessa säätietoja"
                }
            }
        }
    }
}

