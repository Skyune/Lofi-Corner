package com.bawp.jetweatherforecast.network

import com.bawp.jetweatherforecast.model.Weather
import com.bawp.jetweatherforecast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "/v1/playlists/MaOAx/tracks?app_name=EXAMPLEAPP")
    suspend fun getWeather(): Weather
}