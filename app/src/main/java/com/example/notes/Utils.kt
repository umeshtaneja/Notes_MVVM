package com.example.notes

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object{

        fun openImageChooser(activity: AddNoteActivity, pickImageRequest: Int) {

            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                activity.startActivityForResult(takePictureIntent, pickImageRequest)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(activity,"Error", Toast.LENGTH_LONG).show()
            }
        }

        fun showImage(context: Context, itemView: ImageView, imgUri: Uri) {

            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform()

            Glide.with(context)
                .load(imgUri).apply(options).into(itemView)
        }

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


    }

}