package com.bawp.jetweatherforecast.screens

import android.app.Activity
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bawp.jetweatherforecast.WeatherApplication
import dagger.hilt.android.internal.Contexts.getApplication
import javax.inject.Inject

@Composable
fun SettingsScreen(onToggleTheme: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "SETTINGS",
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

    IconButton(
            onClick = onToggleTheme,
            modifier = Modifier.padding(16.dp)
        ) {
        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Toggle Theme")
        }

        }
    }


@Composable
@Preview
fun SettingsScreenPreview() {
    SettingsScreen(onToggleTheme = {})
}