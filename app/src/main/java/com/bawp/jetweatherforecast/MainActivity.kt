package com.bawp.jetweatherforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.bawp.jetweatherforecast.navigation.WeatherNavigation
import com.bawp.jetweatherforecast.navigation.WeatherScreens
import com.bawp.jetweatherforecast.screens.main.MainScreen
import com.bawp.jetweatherforecast.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var application: WeatherApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = application.isDark.value) {
                WeatherApp(onToggleTheme = { application.toggleLightTheme()} )
            }

        }
    }
}

@Composable
fun WeatherApp(onToggleTheme: () -> Unit) {


               Column(verticalArrangement = Arrangement.Center,
                     horizontalAlignment = Alignment.CenterHorizontally) {
                   val navController = rememberNavController()

                   MainScreen(navController = navController, onToggleTheme = onToggleTheme)

               }

        }





@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme() {

    }
}