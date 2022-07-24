package com.example.cleannote.data

import android.provider.ContactsContract
import androidx.room.*
import com.bawp.jetweatherforecast.model.CurrentSong
import com.bawp.jetweatherforecast.model.Data
import kotlinx.coroutines.flow.Flow

@Dao
abstract  interface NoteDatabaseDao {

//    @Query("SELECT * from NOTES_TBL")
//    fun getNotes(): Flow<List<CurrentSong>>
//
//    @Query("SELECT * from NOTES_TBL where id =:id")
//    suspend fun getNotesById(id:String): ContactsContract.CommonDataKinds.Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(song: CurrentSong)

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun update(note: ContactsContract.CommonDataKinds.Note)
//
//    @Query("DELETE from notes_tbl")
//    suspend fun deleteAll()
//
//    @Delete
//    suspend fun deleteNote(note: ContactsContract.CommonDataKinds.Note)
}