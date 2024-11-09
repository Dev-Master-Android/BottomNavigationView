package com.example.retrofiteweatherapi.models

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