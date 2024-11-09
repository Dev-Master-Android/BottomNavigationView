package com.example.retrofiteweatherapi.models

data class CurrentWeather(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val rain: Rain,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)
/**
 * Текущая погода
 * @property base - базовая часть ответа
 * @property clouds - облачность
 * @property cod - код ответа
 * @property coord - координаты
 * @property dt - момент времени (unix, UTC)
 * @property id - id города
 * @property main - основные показатели погоды
 * @property name - название города
 * @property rain - количество осадков
 * @property sys - системные данные
 * @property timezone - разница от UTC
 * @property visibility - видимость
 * @property weather - описание погоды
 * @property wind - ветер
 */


