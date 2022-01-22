package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)

    @Query("Select * from my_Notes_table order by id DESC")
    fun getNotes() : LiveData<List<Note>>

    @Query("SELECT * FROM my_Notes_table WHERE notes_title LIKE :searchQuery OR notes_content LIKE :searchQuery order by id DESC")
    fun searchNotes(searchQuery: String) : LiveData<List<Note>>

}