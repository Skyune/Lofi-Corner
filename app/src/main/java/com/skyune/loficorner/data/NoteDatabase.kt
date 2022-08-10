package com.example.cleannote.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skyune.loficorner.model.CurrentSong


@Database(entities = [CurrentSong::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDatabaseDao
}