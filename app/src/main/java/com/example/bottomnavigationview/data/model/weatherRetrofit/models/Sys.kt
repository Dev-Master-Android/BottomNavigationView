package com.example.retrofiteweatherapi.models

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)
/**
 * Информация о системе
 * @property country - код страны
 * @property id - внутренний id
 * @property sunrise - время восхода солнца
 * @property sunset - время заката
 * @property type - тип
 */