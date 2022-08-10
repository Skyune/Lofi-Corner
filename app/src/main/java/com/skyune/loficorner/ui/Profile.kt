@file:OptIn(ExperimentalFoundationApi::class)

package com.skyune.loficorner.ui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.skyune.loficorner.data.DataOrException
import com.skyune.loficorner.exoplayer.MusicServiceConnection
import com.skyune.loficorner.model.CurrentSong
import com.skyune.loficorner.model.Data
import com.skyune.loficorner.model.Weather
import com.skyune.loficorner.utils.playMusic
import com.skyune.loficorner.utils.playMusicFromId
import java.sql.Types.NULL


@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel(),
                  musicServiceConnection: MusicServiceConnection) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        val songList = profileViewModel.noteList.collectAsState().value
        ShowData(profileViewModel, songList, musicServiceConnection)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowData(profileViewModel: ProfileViewModel, songList: List<CurrentSong>,musicServiceConnection: MusicServiceConnection) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = profileViewModel.getWeatherData()
    }.value

    val isPlayerReady: MutableState<Boolean> = rememberSaveable{
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth().fillMaxHeight()
            .background(Color(0xFFC1AEB9)),
        contentAlignment = Alignment.Center,

        ) {

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {

            LazyColumn(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)) {
               item {
                   Text("here goes")
               }
                stickyHeader {

                        Text("Your Lofi Corner,",
                            style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold,
                            fontSize= 20.sp)
                }
                items(weatherData.data!!.data) { item ->
                    //wait... is this a bad idea to save every song in a room database?
                    //its not a bug its a feature
                    //you can probably implement previous and next buttons for LITERALLY every song played.
                    WeatherItem(item, onItemClicked = {
                        if (isPlayerReady.value) {
                            isPlayerReady.value = false
                        }
                        playMusicFromId(musicServiceConnection, weatherData.data!!.data, item.id, isPlayerReady.value)
                        isPlayerReady.value = true


                        profileViewModel.addNote(
                            CurrentSong(
                                NULL,
                                item.id,
                                item.duration,
                                item.title
                            )
                        )
                    })
                }

            }
            //progress i guess
            if(songList.isNotEmpty())
            Text(songList.last().title)

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
    //ProfileScreen(musicServiceConnection = MusicServiceConnection())
}