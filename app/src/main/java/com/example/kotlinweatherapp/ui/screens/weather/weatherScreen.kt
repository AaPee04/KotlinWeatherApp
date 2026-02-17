package com.example.kotlinweatherapp.ui.screens.weather

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlinweatherapp.ui.components.SearchBar
import com.example.kotlinweatherapp.ui.components.WeatherContentFromEntity

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {

    val searchQuery by viewModel.searchQuery.collectAsState()
    val weather by viewModel.weather.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val history by viewModel.searchHistory.collectAsState(initial = emptyList())

    var showHistory by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {

            Text(
                text = "SÄÄAPPI",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = MaterialTheme.colorScheme.primary,
                        blurRadius = 30f
                    )
                ),
                modifier = Modifier.align(Alignment.Center)
            )

            Text(
                text = if (showHistory) "Takaisin" else "Historia",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .clickable { showHistory = !showHistory }
            )
        }


        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.onSearchQueryChange(it) },
            onSearch = { viewModel.searchWeather() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (showHistory) {

            if (history.isEmpty()) {
                Text(
                    text = "Ei hakuhistoriaa vielä",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                history.take(10).forEach { item ->
                    Text(
                        text = item.city,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onSearchQueryChange(item.city)
                                viewModel.searchWeather()
                                showHistory = false
                            }
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                }
            }

        } else {

            when {
                errorMessage != null -> {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                weather == null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                else -> {
                    WeatherContentFromEntity(weather!!)
                }
            }
        }
    }
}
