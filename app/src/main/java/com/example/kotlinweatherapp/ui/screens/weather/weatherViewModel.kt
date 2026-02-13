package com.example.kotlinweatherapp.ui.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinweatherapp.data.model.WeatherResponse
import com.example.kotlinweatherapp.data.repository.WeatherRepository
import com.example.kotlinweatherapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    private val _weatherState = MutableStateFlow<Result<WeatherResponse>?>(null)
    val weatherState: StateFlow<Result<WeatherResponse>?> = _weatherState.asStateFlow()

    private val _searchQuery = MutableStateFlow("Helsinki")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun searchWeather() {
        val city = _searchQuery.value
        if (city.isBlank()) return

        viewModelScope.launch {
            _weatherState.value = Result.Loading
            _weatherState.value = repository.getWeather(city)
        }
    }
}
