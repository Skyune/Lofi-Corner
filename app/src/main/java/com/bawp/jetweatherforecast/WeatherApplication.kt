package com.bawp.jetweatherforecast

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.bawp.jetweatherforecast.ui.theme.Theme
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication: Application() {

    //should be saved in cache or datastore
    private val isDark = mutableStateOf(false)
    public val currentTheme = mutableStateOf(Theme.Light)

    fun toggleLightTheme() {
        isDark.value = !isDark.value
    }

    fun changeTheme(currentTheme: Theme) {
        this.currentTheme.value = currentTheme
    }

    //multiple themes


//    fun toggleLightTheme() {
//        currentRoom = roomTitle
//    }
}