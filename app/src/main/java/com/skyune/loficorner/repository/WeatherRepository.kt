package com.skyune.loficorner.repository

import android.util.Log
import com.skyune.loficorner.data.DataOrException
import com.skyune.loficorner.model.CurrentSong
import com.skyune.loficorner.model.Weather
import com.skyune.loficorner.network.WeatherApi
import com.example.cleannote.data.NoteDatabaseDao
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi, private val noteDatabaseDao: NoteDatabaseDao) {

    suspend fun getWeather()
    :DataOrException<Weather, Boolean, Exception>  {
        val response = try {
            api.getWeather()

        }catch (e: Exception){
            Log.d("REX", "getWeather: $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE", "getWeather: $response")
        return  DataOrException(data = response)

    }


    suspend fun addNote(currentSong: CurrentSong) = noteDatabaseDao.insert(currentSong)
    suspend fun getAllNotes() = noteDatabaseDao.getNotes()
    suspend fun getLatestNote() = noteDatabaseDao.getLatest()

}