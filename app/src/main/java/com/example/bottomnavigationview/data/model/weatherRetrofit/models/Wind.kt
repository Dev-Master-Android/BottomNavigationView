package com.example.retrofiteweatherapi.models

data class Wind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)
/**
 * Класс Wind содержит информацию о ветре.
 *
 * @property deg направление ветра в градусах
 * @property gust скорость порывов ветра в метрах в секунду
 * @property speed скорость ветра в метрах в секунду
 */