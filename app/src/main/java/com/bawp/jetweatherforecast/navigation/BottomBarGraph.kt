package com.bawp.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bawp.jetweatherforecast.screens.BottomNavScreen
import com.bawp.jetweatherforecast.screens.HomeScreen
import com.bawp.jetweatherforecast.screens.ProfileScreen
import com.bawp.jetweatherforecast.screens.SettingsScreen

//@Composable
//fun BottomNavGraph(navController: NavHostController) {
//    NavHost(
//        navController = navController,
//        startDestination = BottomNavScreen.Home.route
//    ) {
//        composable(route = BottomNavScreen.Home.route) {
//            HomeScreen()
//        }
//        composable(route = BottomNavScreen.Profile.route) {
//            ProfileScreen()
//        }
//        composable(route = BottomNavScreen.Settings.route) {
//            SettingsScreen()
//        }
//    }
//}