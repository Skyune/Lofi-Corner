package com.bawp.jetweatherforecast.screens

import androidx.lifecycle.ViewModel
import com.bawp.jetweatherforecast.data.DataOrException
import com.bawp.jetweatherforecast.model.Weather
import com.bawp.jetweatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: WeatherRepository)
    : ViewModel() {
    suspend fun getWeatherData()
            : DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather()

    }
}