package com.skyune.loficorner.ui.profileScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skyune.loficorner.data.DataOrException
import com.skyune.loficorner.model.CurrentSong
import com.skyune.loficorner.model.Data
import com.skyune.loficorner.model.Weather
import com.skyune.loficorner.repository.WeatherRepository
import com.yeocak.parallaximage.GravitySensorDefaulted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
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

     fun getMovieById(id: String) : Call<Weather> {
        return repository.getMovieById(id)

    }
    fun getPlaylist(id: String) : Call<Weather> {
        return repository.getPlaylist(id)

    }



    private val _noteList = MutableStateFlow<List<CurrentSong>>(emptyList())
    var noteList = _noteList.asStateFlow()

    private val _playlist by mutableStateOf(emptyList<Data>())
    var playlist = _playlist


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



