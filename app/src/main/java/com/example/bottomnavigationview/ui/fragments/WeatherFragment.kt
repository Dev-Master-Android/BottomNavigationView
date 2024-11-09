package com.example.bottomnavigationview.ui.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bottomnavigationview.R
import com.example.bottomnavigationview.databinding.FragmentWeatherBinding
import com.example.bottomnavigationview.viewmodel.WeatherViewModel
import com.example.retrofiteweatherapi.utils.RetrofitInstance
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private lateinit var temperatureTV: TextView
    private lateinit var maxTemperatureTV: TextView
    private lateinit var minTemperatureTV: TextView
    private lateinit var cityTV: TextView
    private lateinit var weatherIV: ImageView
    private lateinit var windDegreeTV: TextView
    private lateinit var windSpeedTV: TextView
    private lateinit var pressureTV: TextView
    private lateinit var humidityTV: TextView
    private lateinit var editTextET: EditText
    private lateinit var searchBTN: Button
    private lateinit var locationBTN: Button

    @SuppressLint("MissingInflatedId")
    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        temperatureTV = view.findViewById(R.id.temperature_tv)
        maxTemperatureTV = view.findViewById(R.id.max_temperature_tv)
        minTemperatureTV = view.findViewById(R.id.min_temperature_tv)
        humidityTV = view.findViewById(R.id.humidity_tv)
        cityTV = view.findViewById(R.id.city_tv)
        editTextET = view.findViewById(R.id.et_city)
        weatherIV = view.findViewById(R.id.weather_iv)
        windDegreeTV = view.findViewById(R.id.wind_degree_tv)
        windSpeedTV = view.findViewById(R.id.wind_speed_tv)
        pressureTV = view.findViewById(R.id.pressure_tv)
        searchBTN = view.findViewById(R.id.btn_search)
        locationBTN = view.findViewById(R.id.btn_location)

        searchBTN.setOnClickListener {
            searchCity()
        }

        locationBTN.setOnClickListener {
            getCurrentLocation()
        }
    }

    private fun searchCity() {
        val city = editTextET.text.toString()
        if (city.isEmpty()) {
            Toast.makeText(
                context,
                "Пожалуйста, введите название города",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getWeatherData(
                    city = city,
                    units = "metric",
                    apiKey = getString(R.string.api_key)
                )
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Ошибка приложения: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@launch
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Ошибка HTTP: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val data = response.body()!!
                    cityTV.text = data.name
                    temperatureTV.text = "${data.main.temp} °C"
                    maxTemperatureTV.text = "Макс: ${data.main.temp_max} °C"
                    minTemperatureTV.text = "Мин: ${data.main.temp_min} °C"
                    windDegreeTV.text = "Направление ветра: ${data.wind.deg}°"
                    windSpeedTV.text = "Скорость ветра: ${data.wind.speed} м/с"
                    val convertPressure = (data.main.pressure / 1.33).toInt()
                    pressureTV.text = "Давление: $convertPressure мм рт. ст."
                    humidityTV.text = "Влажность: ${data.main.humidity}%"
                    val iconId = data.weather[0].icon
                    val iconUrl = "https://openweathermap.org/img/wn/$iconId@4x.png"
                    Picasso.get().load(iconUrl).into(weatherIV)
                }
            }
        }
    }

    private fun getCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val lat = location.latitude
                    val lon = location.longitude

                    GlobalScope.launch(Dispatchers.IO) {
                        val response = try {
                            RetrofitInstance.api.getWeatherLocationData(
                                lat = lat,
                                lon = lon,
                                units = "metric",
                                apiKey = getString(R.string.api_key)
                            )
                        } catch (e: IOException) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Ошибка приложения: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            return@launch
                        } catch (e: HttpException) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Ошибка HTTP: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            return@launch
                        }

                        if (response.isSuccessful && response.body() != null) {
                            withContext(Dispatchers.Main) {
                                val data = response.body()!!
                                cityTV.text = data.name
                                temperatureTV.text = "${data.main.temp} °C"
                                maxTemperatureTV.text = "Макс: ${data.main.temp_max} °C"
                                minTemperatureTV.text = "Мин: ${data.main.temp_min} °C"
                                windDegreeTV.text = "Направление ветра: ${data.wind.deg}°"
                                windSpeedTV.text = "Скорость ветра: ${data.wind.speed} м/с"
                                val convertPressure = (data.main.pressure / 1.33).toInt()
                                pressureTV.text = "Давление: $convertPressure мм рт. ст."
                                humidityTV.text = "Влажность: ${data.main.humidity}%"
                                val iconId = data.weather[0].icon
                                val iconUrl = "https://openweathermap.org/img/wn/$iconId@4x.png"
                                Picasso.get().load(iconUrl).into(weatherIV)
                            }
                        }
                    }
                }
            }
    }
}