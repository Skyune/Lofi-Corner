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
   private val _loaded by mutableStateOf(false)
 var loaded = _loaded
    val playlistids = listOf("noPJL", "n62mn","ebd1O", "eAlov", "nQR49", "nqbzB", "ezWJp", "lzdql", "XB7R7", "5QaVY", "qE1q2","3AbWv", "AxRP0", "aAw5Q", "Q4wGW", "KK8v2", "RKjdZ","epYaM",
        "LKpEw", "Dv65v", "ebOpP", "ePMJ5", "Ax7ww", "OgyN4", "Lw2x2","eG0wm","eJb37", "nq47M", "nZKEQ","Ax6NK","x5XM9")

    //    private val _playlist by mutableStateOf(emptyList<Data>())
//    var playlist = _playlist
    var myList: MutableList<Data> = mutableListOf<Data>()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("indi","${playlistids.indices}")
            for (i in playlistids.indices) {

                val response: Call<Weather> = repository.getPlaylist(playlistids[i])
                response.enqueue(object : retrofit2.Callback<Weather> {
                    override fun onFailure(call: Call<Weather>, t: Throwable) {
                        Log.d("onFailure", t.message.toString())
                    }

                    override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                        Log.d("onResponse", response.body().toString())
                        if (response.isSuccessful) {
                            myList.add(response.body()!!.data[0])

                        }
                    }
                })
            }

            loaded = true
        }


    }
    private val _noteList = MutableStateFlow<List<CurrentSong>>(emptyList())
    var noteList = _noteList.asStateFlow()


//        init {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getAllNotes().distinctUntilChanged()
//                .collect() {
//                    _noteList.value = it
//                }
//        }
//        //noteList.addAll(NotesDataSource().loadNotes())
//    }


    fun addNote(song: CurrentSong) = viewModelScope.launch { repository.addNote(song) }
    //fun getNotes() = viewModelScope.launch {  repository.getLatestNote() }

}



