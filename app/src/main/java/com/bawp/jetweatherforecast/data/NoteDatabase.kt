package com.example.cleannote.data

import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bawp.jetweatherforecast.model.CurrentSong
import com.bawp.jetweatherforecast.model.Data


@Database(entities = [CurrentSong::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDatabaseDao
}