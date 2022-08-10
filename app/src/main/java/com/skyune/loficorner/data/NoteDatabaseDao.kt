package com.example.cleannote.data

import androidx.room.*
import com.skyune.loficorner.model.CurrentSong
import kotlinx.coroutines.flow.Flow

@Dao
abstract  interface NoteDatabaseDao {

    @Query("SELECT * from NOTES_TBL")
    fun getNotes(): Flow<List<CurrentSong>>

    @Query("SELECT * FROM NOTES_TBL ORDER BY id DESC LIMIT 1" )
    fun getLatest(): Flow<List<CurrentSong>>
//
//    @Query("SELECT * from NOTES_TBL where id = MAX(id)")
//    suspend fun getNotesById(id:String): String

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
