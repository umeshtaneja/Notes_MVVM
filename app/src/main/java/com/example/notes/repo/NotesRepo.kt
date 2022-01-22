package com.example.notes.repo

import androidx.lifecycle.LiveData
import com.example.notes.database.Note
import com.example.notes.database.NotesDao

class NotesRepo(private var notesDao: NotesDao) {

    val allNotes : LiveData<List<Note>> = notesDao.getNotes()

    suspend fun insertNote (note:Note){
        notesDao.insertNote(note)
    }



}