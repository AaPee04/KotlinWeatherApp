package com.example.kotlinweatherapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlinweatherapp.data.model.WeatherResponse

// 📁 ui/components/WeatherContent.kt
// Näyttää haetut säätiedot: kaupunki, kuvake, lämpötila, lisätiedot.
// AsyncImage (Coil) lataa sääkuvakkeen verkosta.

@Composable
fun WeatherContent(weather: WeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())  // Scrollattava sisältö
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Kaupunki ja maa
        Text(
            text = "${weather.name}, ${weather.sys.country}",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sääkuvake (ladataan verkosta Coil-kirjastolla)
        val iconUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}@4x.png"
        AsyncImage(
            model = iconUrl,
            contentDescription = "Sääkuvake",
            modifier = Modifier.size(120.dp)
        )

        // Lämpötila
        Text(
            text = "${weather.main.temp.toInt()}°C",
            style = MaterialTheme.typography.displayLarge
        )

        // Kuvaus (esim. "pilvistä" → "Pilvistä")
        Text(
            text = weather.weather[0].description.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Lisätiedot kortissa
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                WeatherDetailRow("Tuntuu kuin", "${weather.main.feels_like.toInt()}°C")
                WeatherDetailRow("Min / Max", "${weather.main.tempMin.toInt()}°C / ${weather.main.tempMax.toInt()}°C")
                WeatherDetailRow("Kosteus", "${weather.main.humidity}%")
                WeatherDetailRow("Tuuli", "${weather.wind.speed} m/s")
                WeatherDetailRow("Paine", "${weather.main.pressure} hPa")
            }
        }
    }
}

// Yksittäinen tietorivi: "Kosteus    85%"
@Composable
fun WeatherDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}