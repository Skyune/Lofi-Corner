@file:OptIn(ExperimentalFoundationApi::class)

package com.skyune.loficorner.ui.profileScreen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.android.exoplayer2.extractor.mp4.Track
import com.skyune.loficorner.data.DataOrException
import com.skyune.loficorner.exoplayer.MusicServiceConnection
import com.skyune.loficorner.model.CurrentSong
import com.skyune.loficorner.model.Data
import com.skyune.loficorner.model.Weather
import com.skyune.loficorner.ui.homeScreen.WeatherItem
import com.skyune.loficorner.ui.profileScreen.components.RoomImagesRow
import com.skyune.loficorner.utils.playMusicFromId
import retrofit2.Call
import retrofit2.Response


@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    musicServiceConnection: MusicServiceConnection,
    bottomBarState: MutableState<Boolean>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        val songList = profileViewModel.noteList.collectAsState().value
        ShowData(profileViewModel, songList, musicServiceConnection, bottomBarState)
    }
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}




@Composable
fun ShowData(
    profileViewModel: ProfileViewModel,
    songList: List<CurrentSong>,
    musicServiceConnection: MusicServiceConnection,
    bottomBarState: MutableState<Boolean>
) {

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = profileViewModel.getWeatherData()
    }.value


    val ctx = LocalContext.current

    val userName = remember {
        mutableStateOf(TextFieldValue())
    }
    val favourites = remember { mutableStateListOf<Data>() }

    val resp = remember {
        mutableStateOf("")
    }


    val listState = rememberLazyListState()
    val scrollingUp = listState.isScrollingUp()


    Log.d("gag", "ShowData: scrollingUp: $scrollingUp")

    val isPlayerReady: MutableState<Boolean> = rememberSaveable{
        mutableStateOf(false)
    }
          bottomBarState.value =  scrollingUp // If we're scrolling up, show the bottom bar

    val response : Call<Weather> = profileViewModel.getPlaylist("noPJL")
    response.enqueue(object : retrofit2.Callback<Weather> {
        override fun onFailure(call: Call<Weather>, t: Throwable) {
            Log.d("onFailure", t.message.toString())
        }

        override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
            Log.d("onResponse", response.body().toString())
            if(response.isSuccessful) {
                profileViewModel.playlist = response.body()!!.data
            }
        }
    })



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFFC1AEB9)),
        contentAlignment = Alignment.Center,

        ) {

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data!!.data != null) {


            LazyColumn(modifier = Modifier
                .padding(2.dp), contentPadding = PaddingValues(1.dp), state = listState) {

                item { RoomImagesRow() }

                
                item { profileViewModel.playlist.forEach {
                    com.skyune.loficorner.ui.profileScreen.WeatherItem(item = it) {
                        
                    }
                }
                 }
                items(weatherData.data!!.data) { item ->


                    WeatherItem(item, onItemClicked = {
                        val response : Call<Weather> = profileViewModel.getMovieById("${item.id}")
                        response.enqueue(object : retrofit2.Callback<Weather> {
                            override fun onFailure(call: Call<Weather>, t: Throwable) {
                                Log.d("onFailure", t.message.toString())
                            }

                            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                                if (isPlayerReady.value) {
                                    isPlayerReady.value = false
                                }
                                playMusicFromId(musicServiceConnection, response.body()!!.data, item.id, isPlayerReady.value)
                                isPlayerReady.value = true
                            }
                        })
                    })
                }
            }
            //progress i guess
            if(songList.isNotEmpty()) {
                //Text(songList.last().title)
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
                Text(text = item.playlist_name)
                //Text(text = item.user.name.toString())
            }
        }
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    //ProfileScreen(musicServiceConnection = MusicServiceConnection())
}