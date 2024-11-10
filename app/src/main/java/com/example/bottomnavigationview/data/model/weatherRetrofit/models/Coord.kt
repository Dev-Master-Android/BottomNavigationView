package com.example.bottomnavigationview.data.model.weatherRetrofit.models

/**
 * Координаты города
 *
 * @property lat - широта
 * @property lon - долгота
 */
data class Coord(
    /**
     * Широта
     */
    val lat: Double,
    /**
     * Долгота
     */
    val lon: Double
)