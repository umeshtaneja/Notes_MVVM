package com.example.notes.database

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_Notes_table")
class Note4(@ColumnInfo(name = "notes_title") val title: String,
           @ColumnInfo(name = "notes_content") val content: String,
           @ColumnInfo(name = "notes_image_uri") val img_uri: Uri?
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}