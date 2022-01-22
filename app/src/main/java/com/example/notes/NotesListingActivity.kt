package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.adapters.NotesAdapter
import com.example.notes.database.Note
import com.example.notes.vm.NotesViewModel
import kotlinx.android.synthetic.main.activity_notes_listing.*

class NotesListingActivity : AppCompatActivity(), NotesAdapter.INoteAdapter {

    private val noteVM : NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_listing)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesAdapter (this,this)
        recyclerView.adapter = adapter

        noteVM.allNotes.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }
        })

    }

    fun addNewNote(view: View) {
        startActivity(Intent(this,AddNoteActivity::class.java))
    }

    override fun onItemClicked(note: Note) {

    }

}