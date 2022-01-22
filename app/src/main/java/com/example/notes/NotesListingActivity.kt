package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.adapters.NotesAdapter
import com.example.notes.database.Note
import com.example.notes.vm.NotesViewModel
import kotlinx.android.synthetic.main.activity_notes_listing.*

/**
 * Activity to show the listing of notes added and handles the search functionality
 */
class NotesListingActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val noteVM : NotesViewModel by viewModels()
    lateinit var adapter : NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_listing)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NotesAdapter (this)
        recyclerView.adapter = adapter

        noteVM.allNotes.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }
        })

        et_search_notes?.setOnQueryTextListener(this)

    }

    /**
     * to start the new Activity to add note
     */
    fun addNewNote(view: View) {
        startActivity(Intent(this,AddNoteActivity::class.java))
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    /**
     * query from room db to get the matched notes
     * and update the adapter with the latest data
     */
    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        noteVM.searchNotes(searchQuery).observe(this,{ notes ->
            notes.let {
                adapter.updateList(notes)
            }
        })
    }

}