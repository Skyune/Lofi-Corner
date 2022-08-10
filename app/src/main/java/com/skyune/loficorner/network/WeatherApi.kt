package com.skyune.loficorner.network

import com.skyune.loficorner.model.Weather
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "/v1/playlists/MaOAx/tracks?app_name=EXAMPLEAPP")
    suspend fun getWeather(): Weather
}