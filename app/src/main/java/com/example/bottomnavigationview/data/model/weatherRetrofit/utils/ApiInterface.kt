package com.example.retrofiteweatherapi.utils

import com.example.retrofiteweatherapi.models.CurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather?")
    suspend fun getWeatherData(
        @Query("q") city: String? = null,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Response<CurrentWeather>

    @GET("weather?")
    suspend fun getWeatherLocationData(
        @Query("lat") lat: Double? = null,
        @Query("lon") lon: Double? = null,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Response<CurrentWeather>
}