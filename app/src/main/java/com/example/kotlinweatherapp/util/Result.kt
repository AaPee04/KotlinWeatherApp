package com.example.kotlinweatherapp.util

// 📁 util/Result.kt
// Sealed class = rajattu joukko mahdollisia tiloja.
// Kääntäjä varmistaa, että when-lausekkeessa käsitellään kaikki tilat.

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()          // Onnistunut vastaus
    data class Error(val exception: Exception) : Result<Nothing>()  // Virhe
    object Loading : Result<Nothing>()                         // Ladataan...
}