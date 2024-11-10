package com.example.bottomnavigationview.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bottomnavigationview.R
import com.example.bottomnavigationview.databinding.FragmentWeatherBinding
import com.example.bottomnavigationview.data.model.weatherRetrofit.utils.RetrofitInstance
import com.example.bottomnavigationview.databinding.FragmentPersonalInfoBinding
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class WeatherFragment() :
    Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }
    @SuppressLint("MissingInflatedId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearch.setOnClickListener {
           searchCity()
        }

        binding.btnLocation.setOnClickListener {
            getCurrentLocation()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n")
    private fun searchCity() {
        val city = binding.etCity.text.toString()
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
                    binding.cityTv.text = data.name
                    binding.temperatureTv.text = "${data.main.temp} °C"
                    binding.maxTemperatureTv.text = "Макс: ${data.main.temp_max} °C"
                    binding.minTemperatureTv.text = "Мин: ${data.main.temp_min} °C"
                    binding.windDegreeTv.text = "Направление ветра: ${data.wind.deg}°"
                    binding.windSpeedTv.text = "Скорость ветра: ${data.wind.speed} м/с"
                    val convertPressure = (data.main.pressure / 1.33).toInt()
                    binding.pressureTv.text = "Давление: $convertPressure мм рт. ст."
                    binding.humidityTv.text = "Влажность: ${data.main.humidity}%"
                    val iconId = data.weather[0].icon
                    val iconUrl = "https://openweathermap.org/img/wn/$iconId@4x.png"
                    Picasso.get().load(iconUrl).into(binding.weatherIv)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
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
                                binding.cityTv.text = data.name
                                binding.temperatureTv.text = "${data.main.temp} °C"
                                binding.maxTemperatureTv.text = "Макс: ${data.main.temp_max} °C"
                                binding.minTemperatureTv.text = "Мин: ${data.main.temp_min} °C"
                                binding.windDegreeTv.text = "Направление ветра: ${data.wind.deg}°"
                                binding.windSpeedTv.text = "Скорость ветра: ${data.wind.speed} м/с"
                                val convertPressure = (data.main.pressure / 1.33).toInt()
                                binding.pressureTv.text = "Давление: $convertPressure мм рт. ст."
                                binding.humidityTv.text = "Влажность: ${data.main.humidity}%"
                                val iconId = data.weather[0].icon
                                val iconUrl = "https://openweathermap.org/img/wn/$iconId@4x.png"
                                Picasso.get().load(iconUrl).into(binding.weatherIv)
                            }
                        }
                    }
                }
            }

   }

}