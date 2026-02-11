package com.example.kotlinweatherapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

// 📁 ui/components/SearchBar.kt
// Uudelleenkäytettävä hakukenttä.
// Callback-funktiot (onQueryChange, onSearch) nostavat tapahtumien
// käsittelyn ylemmälle tasolle (State Hoisting -periaate).

@Composable
fun SearchBar(
    query: String,                    // Nykyinen hakuteksti
    onQueryChange: (String) -> Unit,  // Kutsutaan kun teksti muuttuu
    onSearch: () -> Unit              // Kutsutaan kun käyttäjä painaa "Hae"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            label = { Text("Kaupunki") },
            modifier = Modifier.weight(1f),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search    // Näppäimistön "Hae"-painike
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch() }       // Enter = hae
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = onSearch) {
            Icon(Icons.Default.Search, "Hae")
        }
    }
}