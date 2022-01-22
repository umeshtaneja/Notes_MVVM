package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)

    @Query("Select * from my_Notes_table order by id ASC")
    fun getNotes() : LiveData<List<Note>>

}