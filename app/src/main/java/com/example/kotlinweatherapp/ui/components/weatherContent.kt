package com.example.kotlinweatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kotlinweatherapp.data.model.WeatherResponse

@Composable
fun WeatherContent(weather: WeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "${weather.name}, ${weather.sys.country}",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        val iconUrl =
            "https://openweathermap.org/img/wn/${weather.weather[0].icon}@4x.png"

        Box(
            contentAlignment = Alignment.Center, // Kunnan nimi
            modifier = Modifier
                .size(170.dp)
                .shadow(35.dp, shape = CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                            MaterialTheme.colorScheme.surfaceVariant
                        )
                    ),
                    shape = CircleShape
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        ) {
            AsyncImage(
                model = iconUrl, // Säänkuva
                contentDescription = "Sääkuvake",
                modifier = Modifier.size(115.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "${weather.main.temp.toInt()}°C", // Lämpötila
            style = MaterialTheme.typography.displayLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                shadow = Shadow(
                    color = MaterialTheme.colorScheme.primary,
                    blurRadius = 25f
                )
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = weather.weather[0].description.replaceFirstChar { it.uppercase() }, // Sään tila
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) { // tietojen listaus

                WeatherDetailRow("Tuntuu kuin", "${weather.main.feels_like.toInt()}°C")
                WeatherDetailRow(
                    "Min / Max",
                    "${weather.main.tempMin.toInt()}°C / ${weather.main.tempMax.toInt()}°C"
                )
                WeatherDetailRow("Kosteus", "${weather.main.humidity}%")
                WeatherDetailRow("Tuuli", "${weather.wind.speed} m/s")
                WeatherDetailRow("Paine", "${weather.main.pressure} hPa")
            }
        }
    }
}

@Composable
fun WeatherDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
