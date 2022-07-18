package com.bawp.jetweatherforecast.screens.main

import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bawp.jetweatherforecast.data.DataOrException
import com.bawp.jetweatherforecast.model.Weather
import com.bawp.jetweatherforecast.navigation.WeatherNavigation
import com.bawp.jetweatherforecast.screens.BottomNavScreen
import com.bawp.jetweatherforecast.widgets.LofiAppBar


@Composable
fun MainScreen(navController: NavController,
               mainViewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            LofiAppBar(title = "Lofi Corner", navController = navController, onAddActionClicked = {}) {
                //trailing lambda
                Log.d("TAG", "MainScreen: button clicked")
            }
        },
        bottomBar = { BottomBar(navController = navController) }
    ) {
        WeatherNavigation(navController = navController)
    }


}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Profile,
        BottomNavScreen.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(modifier = Modifier.clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
        backgroundColor = Color(0xFFCDBEC8), elevation = 40.dp) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

