package com.example.retrofiteweatherapi.models

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)
/**
 * Описание погоды
 *
 * @property description - описание погоды
 * @property icon - иконка погоды
 * @property id - id погоды
 * @property main - основное описание погоды
 */