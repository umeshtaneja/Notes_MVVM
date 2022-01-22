package com.example.notes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.Utils
import com.example.notes.database.Note

class NotesAdapter(private val context : Context) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val viewHolder  =  NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note,parent,false))
        viewHolder.imgShare.setOnClickListener {
            Utils.dispatchShareNoteIntent(context,allNotes[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.noteTitle.text = currentNote.title
        holder.noteContent.text = currentNote.content
        Utils.showImage(context,holder.noteImg,currentNote.img_uri)
    }

    override fun getItemCount(): Int {
       return allNotes.size
    }

    /**
     * method used to update the list of notes
     * after search or insert
     */
    fun updateList(newList : List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }


    inner class NotesViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        val noteTitle: TextView = itemView.findViewById(R.id.tv_notes_title)
        val noteContent: TextView = itemView.findViewById(R.id.tv_note_content)
        val noteImg : ImageView = itemView.findViewById(R.id.iv_note)
        val imgShare : ImageView = itemView.findViewById(R.id.iv_share)
    }
}