package com.bawp.jetweatherforecast.repository

import android.util.Log
import com.bawp.jetweatherforecast.data.DataOrException
import com.bawp.jetweatherforecast.model.Weather
import com.bawp.jetweatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

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

}