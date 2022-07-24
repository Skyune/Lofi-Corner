package com.bawp.jetweatherforecast.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.bawp.jetweatherforecast.data.DataOrException
import com.bawp.jetweatherforecast.model.CurrentSong
import com.bawp.jetweatherforecast.model.Data
import com.bawp.jetweatherforecast.model.Weather


@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        ShowData(profileViewModel)
    }
}

@Composable
fun ShowData(profileViewModel: ProfileViewModel) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = profileViewModel.getWeatherData()
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC1AEB9)),
        contentAlignment = Alignment.Center,

        ) {
        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            LazyColumn(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)) {
                items(weatherData.data!!.data ) { item ->
                    //wait... is this a bad idea to save every song in a room database?
                    //its not a bug its a feature
                    //you can probably implement previous and next buttons for LITERALLY every song played.
                    WeatherItem(item, onItemClicked = {profileViewModel.addNote(CurrentSong(item.id, item.duration,item.title))
                        Log.d("TAG", "ShowData: ${CurrentSong(item.id, item.duration,item.title)}")})
                    }
                }
            }
            //Log.d("apicallback", "ShowData: ${weatherData.data!!.data.toString()}")
        }
    }


@Composable
fun WeatherItem(item: Data, onItemClicked: () -> Unit) {
    Surface(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth()
        .clickable { onItemClicked() },
        color = Color(0xFFCDBEC8),
        shape = RoundedCornerShape(4.dp)) {
        Row() {
            Image(
                rememberImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = item.artwork.small)
                        .build()
                ),
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Column() {
                Text(text = item.title.toString())
                Text(text = item.user.name.toString())
            }
        }
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen()
}