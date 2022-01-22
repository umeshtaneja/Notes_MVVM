package com.example.notes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.notes.database.Note
import com.example.notes.vm.NotesViewModel
import kotlinx.android.synthetic.main.activity_add_note.*
import java.io.File
import java.io.IOException

/**
 * This activity is used to enter the new note.
 */
class AddNoteActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1
    var photoURI: Uri? = null
    private val noteVM : NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.title = "Add Note"
    }


    fun addImage(view: View) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_IMAGE_CAPTURE)
        }else{
            dispatchTakePictureIntent()
        }
    }

    /**
     * method is used to take the image using camera
     */
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    Utils.createImageFile(this)
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        this,
                        "com.example.notes",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
           Utils.showImage(this,iv_note, photoURI.toString())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(this, "camera permission required", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * method is used to add new note to Room DB.
     */
    fun addNoteToDB(view: View) {
        val noteTitle = et_title.text.toString()
        val noteContent = et_content.text.toString()
        if(noteTitle.isNotEmpty() && noteContent.isNotEmpty()){
            noteVM.insertNote(Note(noteTitle,noteContent,photoURI.toString()))
            Toast.makeText(this,"Note Inserted", Toast.LENGTH_LONG).show()
            finish()
        }else{
            Toast.makeText(this,"Please fill all the details.", Toast.LENGTH_LONG).show()
        }
    }


}