package com.example.notes.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notes.database.Note
import com.example.notes.database.NotesDatabase
import com.example.notes.repo.NotesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    val allNotes : LiveData<List<Note>>
    private val noteRepo : NotesRepo

    init {
        val noteDoo = NotesDatabase.getInstance(application).getNodeDao()
        noteRepo =NotesRepo(noteDoo)
        allNotes = noteRepo.allNotes
    }


    fun insertNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepo.insertNote(note)
    }

    fun searchNotes(searchQuery: String) : LiveData<List<Note>>{
       return noteRepo.searchNote(searchQuery)
    }

}