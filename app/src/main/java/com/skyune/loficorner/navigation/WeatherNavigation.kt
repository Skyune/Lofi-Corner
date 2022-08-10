package com.skyune.loficorner.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.skyune.loficorner.exoplayer.MusicServiceConnection
import com.skyune.loficorner.ui.*
import com.skyune.loficorner.ui.main.MainScreen
import com.skyune.loficorner.ui.main.MainViewModel
import com.skyune.loficorner.ui.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation(
    navController: NavHostController,
    onToggleTheme: () -> Unit,
    onToggleDarkMode: () -> Unit,
    musicServiceConnection: MusicServiceConnection
) {
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
                onToggleDarkMode,
                musicServiceConnection
            )
        }
        composable(route = BottomNavScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomNavScreen.Profile.route) {
            ProfileScreen(profileViewModel = hiltViewModel(), musicServiceConnection)
        }
        composable(route = BottomNavScreen.Settings.route) {
            SettingsScreen(onToggleTheme, onToggleDarkMode)
        }

    }
}