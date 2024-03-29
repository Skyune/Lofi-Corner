package com.skyune.loficorner.network

import com.skyune.loficorner.model.Weather
import com.skyune.loficorner.utils.Constants.appName
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "/v1/playlists/search?query=lofi&app_name=$appName")
    suspend fun getWeather(): Weather

    @GET("v1/playlists/{playlistId}/tracks?app_name=$appName")
    fun getMovieById(
        @Path("playlistId") id: String,
    ): Call<Weather>


    @GET("/v1/playlists/{playlist_id}?app_name=$appName")
     fun getPlaylist(
        @Path("playlist_id") id: String,
    ): Call<Weather>
}