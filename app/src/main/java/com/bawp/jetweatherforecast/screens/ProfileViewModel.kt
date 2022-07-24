package com.bawp.jetweatherforecast.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bawp.jetweatherforecast.data.DataOrException
import com.bawp.jetweatherforecast.model.CurrentSong
import com.bawp.jetweatherforecast.model.Data
import com.bawp.jetweatherforecast.model.Weather
import com.bawp.jetweatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: WeatherRepository)
    : ViewModel() {
    suspend fun getWeatherData()
            : DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather()

    }

    private val _currentSong = MutableStateFlow<List<CurrentSong>>(emptyList())
    val currentSongList = _currentSong.asStateFlow()
    fun addNote(song: CurrentSong) = viewModelScope.launch { repository.addNote(song) }


}