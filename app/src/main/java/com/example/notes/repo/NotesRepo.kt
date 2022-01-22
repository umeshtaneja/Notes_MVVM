package com.example.notes.repo

import androidx.lifecycle.LiveData
import com.example.notes.database.Note
import com.example.notes.database.NotesDao

class NotesRepo(private var notesDao: NotesDao) {

    /**
     *  to get notes at real time as new note updated
     */
    val allNotes : LiveData<List<Note>> = notesDao.getNotes()

    /**
     * to insert new note to room db
     */
    suspend fun insertNote (note:Note){
        notesDao.insertNote(note)
    }

    /**
     * to get the searched notes from db
     */
    fun searchNote(searchQuery: String) : LiveData<List<Note>>{
       return notesDao.searchNotes(searchQuery)
    }

}