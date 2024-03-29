package com.skyune.loficorner.exoplayer

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.skyune.loficorner.exoplayer.library.extension.isFavorite
import com.skyune.loficorner.exoplayer.utils.Constants.NETWORK_ERROR
import com.skyune.loficorner.model.Data
import kotlinx.coroutines.*
import javax.inject.Inject

class MusicServiceConnection @Inject constructor(
    private val musicSource: MusicSource,
    context: Context
) {

    val isConnected: MutableState<Boolean> = mutableStateOf(false)

    val isFavorite: MutableMap<String, Boolean> = mutableMapOf()

    val networkError: MutableState<Boolean> = mutableStateOf(false)

    val songDuration: MutableState<Long> = mutableStateOf(0)

    val playbackState: MutableState<PlaybackStateCompat?> =
        mutableStateOf(PlaybackStateCompat.fromPlaybackState(null))

    val sliderClicked: MutableState<Boolean> = mutableStateOf(false)

    val currentPlayingSong: MutableState<MediaMetadataCompat?> =
        mutableStateOf(MediaMetadataCompat.fromMediaMetadata(null))

    lateinit var mediaController: MediaControllerCompat

    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(context = context)

    private val mediaBrowser = MediaBrowserCompat(
        context,
        ComponentName(
            context,
            MusicService::class.java
        ),
        mediaBrowserConnectionCallback,
        null
    ).apply {
        connect()
        updateSong()
    }

    val transportControls: MediaControllerCompat.TransportControls
        get() = mediaController.transportControls

    val shuffleMode: Int
        get() = mediaController.shuffleMode

    val repeatMode: Int
        get() = mediaController.repeatMode

    fun updatePlaylist(list: List<Data>) {
        musicSource.playlist = list
        musicSource.fetchMediaData()
    }


    fun updateSong() {
        val serviceScope = CoroutineScope(Dispatchers.IO)

        serviceScope.launch {
            while (!sliderClicked.value) {
                ensureActive()
                delay(100L)
                val pos = playbackState.value?.currentPlaybackPosition
                if (songDuration.value != pos) {
                    pos?.let {
                        songDuration.value = it
                    }
                }
                delay(900L)
            }
        }
    }

    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.subscribe(parentId, callback)
    }

    fun unsubscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.unsubscribe(parentId, callback)
    }

    private inner class MediaBrowserConnectionCallback(
        private val context: Context
    ) : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallback())
            }
            isConnected.value = true
        }

        override fun onConnectionSuspended() {
            isConnected.value = false
        }

        override fun onConnectionFailed() {
            isConnected.value = false
        }
    }

    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            playbackState.value = state
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            currentPlayingSong.value = metadata

            currentPlayingSong.value?.description?.mediaId?.let {
                isFavorite.put(
                    it,
                    isFavorite[it] ?: currentPlayingSong.value?.isFavorite.toBoolean()
                )
            }
        }

        override fun onSessionEvent(event: String?, extras: Bundle?) {
            when (event) {
                NETWORK_ERROR -> networkError.value = true
            }
        }

        override fun onQueueChanged(queue: MutableList<MediaSessionCompat.QueueItem>?) {
        }

        override fun onSessionDestroyed() {
            mediaBrowserConnectionCallback.onConnectionSuspended()
        }
    }
}