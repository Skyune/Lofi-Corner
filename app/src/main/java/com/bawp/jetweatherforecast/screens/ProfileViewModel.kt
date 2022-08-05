package com.bawp.jetweatherforecast.screens

import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bawp.jetweatherforecast.data.DataOrException
import com.bawp.jetweatherforecast.model.CurrentSong
import com.bawp.jetweatherforecast.model.Data
import com.bawp.jetweatherforecast.model.Weather
import com.bawp.jetweatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: WeatherRepository)
    : ViewModel() {
    suspend fun getWeatherData()
            : DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather()

    }
    suspend fun getSongData()
            : DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather()

    }



    private val _noteList = MutableStateFlow<List<CurrentSong>>(emptyList())
    var noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged()
                .collect() {
                    _noteList.value = it
                }
        }
        //noteList.addAll(NotesDataSource().loadNotes())
    }

    fun addNote(song: CurrentSong) = viewModelScope.launch { repository.addNote(song) }
    //fun getNotes() = viewModelScope.launch {  repository.getLatestNote() }

}



