package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Note::class),version = 1,exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNodeDao() : NotesDao

    companion object{

        @Volatile
        private var INSTANCE : NotesDatabase? = null

        fun getInstance(context: Context):NotesDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                                NotesDatabase :: class.java,
                                "Nntes_Database").
                                build()
                INSTANCE = instance
                instance
            }
        }

    }

}