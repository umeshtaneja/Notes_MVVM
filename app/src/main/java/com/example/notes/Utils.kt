package com.example.notes

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.notes.database.Note
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object{

        /**
         * load image using Glide lib
         */
        fun showImage(context: Context, itemView: ImageView, imgUri: String?) {
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform()

            Glide.with(context)
                .load(imgUri?.toUri()).apply(options).into(itemView)
        }

        /**
         * this method returns file path to save the captured image
         */
        fun createImageFile(context: Context): File {
            lateinit var currentPhotoPath: String
            // Create an image file name
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? =  context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            ).apply {
                currentPhotoPath = absolutePath
            }
        }

        /**
         * this method is used to launch the sharing intent with notes details.
         */
        fun dispatchShareNoteIntent(context: Context,note: Note) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Title - " + note.title + "\n" +  " Content - " + note.content)
                type = if(note.img_uri != null && note.img_uri != "null"){
                    putExtra(Intent.EXTRA_STREAM, note.img_uri.toUri())
                    "image/jpeg"
                }else{
                    "text/plain"
                }

            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }


    }

}