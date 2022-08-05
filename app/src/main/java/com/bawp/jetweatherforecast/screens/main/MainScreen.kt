package com.bawp.jetweatherforecast.screens.main

import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bawp.jetweatherforecast.data.DataOrException
import com.bawp.jetweatherforecast.model.CurrentSong
import com.bawp.jetweatherforecast.model.Weather
import com.bawp.jetweatherforecast.navigation.WeatherNavigation
import com.bawp.jetweatherforecast.screens.BottomNavScreen
import com.bawp.jetweatherforecast.widgets.LofiAppBar
import org.intellij.lang.annotations.JdkConstants


@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    onToggleTheme: () -> Unit,
    onToggleDarkMode: () -> Unit
) {
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
        WeatherNavigation(navController = navController, onToggleTheme, onToggleDarkMode)
    }


}

@Composable
fun BottomBar(navController: NavHostController, mainViewModel: MainViewModel = hiltViewModel()) {
    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Profile,
        BottomNavScreen.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {


        Card(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp),)
                .height(50.dp)
                .fillMaxWidth(), backgroundColor = MaterialTheme.colors.background
        ) {
        }
        BottomNavigation(
            modifier = Modifier
                .zIndex(2f)
                .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
            backgroundColor = Color(0xFFCDBEC8), elevation = 10.dp,
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
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

