package com.skyune.loficorner.model

import com.rld.justlisten.datalayer.models.UserModel
import kotlinx.serialization.SerialName

data class Data(

    val artwork: Artwork,
    val description: String,
    val downloadable: Boolean,
    val playlist_name: String,
    val duration: Int,
    val favorite_count: Int,
    val genre: String,
    val id: String,
    val mood: String,
    val permalink: String,
    val play_count: Int,
    val release_date: String,
    val remix_of: RemixOf,
    val repost_count: Int,
    val tags: String,
    val title: String,
    val user: User
)