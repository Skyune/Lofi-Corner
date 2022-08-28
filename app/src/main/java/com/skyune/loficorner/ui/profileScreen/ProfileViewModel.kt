package com.skyune.loficorner.ui.profileScreen

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleannote.data.NotesDataSource
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


    val playlistids = listOf("noPJL", "n62mn","ebd1O", "eAlov", "nQR49", "nqbzB", "ezWJp", "lzdql", "XB7R7", "5QaVY", "qE1q2","3AbWv", "AxRP0", "aAw5Q", "Q4wGW", "KK8v2", "RKjdZ","epYaM",
        "LKpEw", "Dv65v", "ebOpP", "ePMJ5", "Ax7ww", "OgyN4", "Lw2x2","eG0wm","eJb37", "nq47M", "nZKEQ","Ax6NK","x5XM9")

        // noteList.addAll(NotesDataSource().loadNotes())

    private val _noteList = MutableStateFlow<List<CurrentSong>>(emptyList())
    var noteList = _noteList.asStateFlow()

    fun addNote(song: CurrentSong) = viewModelScope.launch { repository.addNote(song) }
    //fun getNotes() = viewModelScope.launch {  repository.getLatestNote() }

}



