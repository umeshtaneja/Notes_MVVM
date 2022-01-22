package com.example.notes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.database.Note

class NotesAdapter(private val context : Context, private val listener : INoteAdapter) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val viewHolder  =  NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note,parent,false))
//        viewHolder.btnDelete.setOnClickListener {
//            listener.onItemClicked(allNotes[viewHolder.adapterPosition])
//        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.textView.text = currentNote.title
    }

    override fun getItemCount(): Int {
       return allNotes.size
    }

    fun updateList(newList : List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }


    inner class NotesViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        val textView = itemView.findViewById<TextView>(R.id.tv_notes)
    }

    interface INoteAdapter{
        fun onItemClicked(note : Note)
    }


}