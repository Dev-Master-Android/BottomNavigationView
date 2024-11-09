package com.example.retrofiteweatherapi.models

data class Main(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)
/**
 * Основные параметры погоды
 * @property feels_like ощущаемая температура
 * @property grnd_level давление на уровне земли
 * @property humidity влажность
 * @property pressure давление
 * @property sea_level давление на уровне моря
 * @property temp температура
 * @property temp_max максимальная температура
 * @property temp_min минимальная температура
 */