package com.bawp.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bawp.jetweatherforecast.screens.BottomNavScreen
import com.bawp.jetweatherforecast.screens.HomeScreen
import com.bawp.jetweatherforecast.screens.ProfileScreen
import com.bawp.jetweatherforecast.screens.SettingsScreen
import com.bawp.jetweatherforecast.screens.main.MainScreen
import com.bawp.jetweatherforecast.screens.main.MainViewModel
import com.bawp.jetweatherforecast.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation(navController: NavHostController, onToggleTheme: () -> Unit, onToggleDarkMode: () -> Unit) {
    //val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = BottomNavScreen.Home.route ) {
        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }

        composable(WeatherScreens.MainScreen.name){
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(
                navController = navController,
                mainViewModel,
                onToggleTheme,
                onToggleDarkMode
            )
        }
        composable(route = BottomNavScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomNavScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = BottomNavScreen.Settings.route) {
            SettingsScreen(onToggleTheme, onToggleDarkMode)
        }

    }
}