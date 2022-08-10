package com.skyune.loficorner.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightThemeColors = lightColors(
    primary = JazzMainScreen,
    primaryVariant = Blue400,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Teal300,
    onSecondary = Black1,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Black1,
)

private val PinkRoomTheme = lightColors(
    primary = Color.Cyan,
    primaryVariant = Blue400,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Teal300,
    onSecondary = Black1,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Black1,
)

private val DarkThemeColors = darkColors(
    primary = JazzMainScreen,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = Black1,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = Black1,
    onSurface = Color.White,
)
private val RainbowTheme = darkColors(
    primary = JazzMainScreen,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = Black1,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = Black1,
    onSurface = Color.White,
)

//@Composable
//fun AppTheme(darkTheme: Boolean = false,
//             content: @Composable() () -> Unit) {
//    val colors = if (darkTheme) DarkThemeColors else LightThemeColors
//    MaterialTheme(
//        colors = colors,
//        typography = MaterialTheme.typography,
//        shapes = MaterialTheme.shapes,
//    ) {
//        content()
//    }
//}
enum class Theme {
    Light,
    Dark,
    Auto
}

@Composable
fun AppTheme(theme: Theme,
             content: @Composable() () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color(0xFFBA98AC)
    )
    val colors = when (theme) {
        Theme.Light -> PinkRoomTheme
        Theme.Dark -> LightThemeColors
        else -> LightThemeColors
    }
    MaterialTheme(

        colors = colors,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
    ) {
        content()
    }
}
