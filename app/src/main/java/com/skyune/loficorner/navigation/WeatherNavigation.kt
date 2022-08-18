package com.skyune.loficorner.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.skyune.loficorner.exoplayer.MusicServiceConnection
import com.skyune.loficorner.ui.*
import com.skyune.loficorner.ui.homeScreen.HomeScreen
import com.skyune.loficorner.ui.mainScreen.MainScreen
import com.skyune.loficorner.ui.mainScreen.MainViewModel
import com.skyune.loficorner.ui.profileScreen.ProfileScreen
import com.skyune.loficorner.ui.settingsScreen.SettingsScreen
import com.skyune.loficorner.ui.splash.WeatherSplashScreen
import com.yeocak.parallaximage.GravitySensorDefaulted

@Composable
fun WeatherNavigation(
    navController: NavHostController,
    onToggleTheme: () -> Unit,
    onToggleDarkMode: () -> Unit,
    musicServiceConnection: MusicServiceConnection,
    gravitySensorDefaulted: GravitySensorDefaulted,
    bottomBarState: MutableState<Boolean>
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
                musicServiceConnection,
                gravitySensorDefaulted,

            )
        }
        composable(route = BottomNavScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomNavScreen.Profile.route) {
            ProfileScreen(profileViewModel = hiltViewModel(), musicServiceConnection,bottomBarState)
        }
        composable(route = BottomNavScreen.Settings.route) {
            SettingsScreen(onToggleTheme, onToggleDarkMode)
        }

    }
}