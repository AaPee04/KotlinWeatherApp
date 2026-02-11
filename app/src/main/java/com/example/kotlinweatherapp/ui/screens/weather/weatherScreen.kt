package com.example.kotlinweatherapp.ui.screens.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kotlinweatherapp.ui.components.SearchBar
import com.example.kotlinweatherapp.ui.screens.weather.WeatherViewModel
import com.example.kotlinweatherapp.util.Result
import com.example.kotlinweatherapp.ui.components.WeatherContent

// 📁 ui/screens/weather/WeatherScreen.kt
// Päänäkymä: kokoaa SearchBarin ja sisällön yhteen.
// collectAsState() = kuuntele StateFlow:ta ja päivitä UI automaattisesti.

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    // collectAsState() muuntaa StateFlown Compose-tilaksi
    val searchQuery by viewModel.searchQuery.collectAsState()
    val weatherState by viewModel.weatherState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Hakukenttä
        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.onSearchQueryChange(it) },
            onSearch = { viewModel.searchWeather() }
        )

        // Säätiedot - when käsittelee kaikki Result-tilat
        when (val state = weatherState) {
            is Result.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Result.Success -> {
                WeatherContent(weather = state.data)
            }
        }
    }
}