package com.example.kotlinweatherapp.ui.screens.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlinweatherapp.ui.components.SearchBar
import com.example.kotlinweatherapp.ui.components.WeatherContent
import com.example.kotlinweatherapp.util.Result

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {

    val searchQuery by viewModel.searchQuery.collectAsState()
    val weatherState by viewModel.weatherState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {

        // 🔥 OTSIKKO
        Text(
            text = "SÄÄAPPI",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = MaterialTheme.colorScheme.primary,
                    blurRadius = 30f
                )
            ),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        // 🔎 HAKUKENTTÄ
        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.onSearchQueryChange(it) },
            onSearch = { viewModel.searchWeather() }
        )

        when (val state = weatherState) {

            null -> {}

            is Result.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            is Result.Success -> {
                WeatherContent(weather = state.data)
            }

            is Result.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.exception.message ?: "Virhe haettaessa säätä",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
