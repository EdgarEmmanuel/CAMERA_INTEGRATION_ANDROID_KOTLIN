package com.example.camera_integration_app

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File


class MainActivity : AppCompatActivity() {
    private var IMAGE_CODE_INDENTIFIER :Int = 1;
    lateinit var imageUploaded:ImageView;
    private lateinit var absoluteLocationPathToPhoto : File;
    private var PHOTO_EXTENSION : String = ".jpg"
    private var PHOTO_NAME : String = "photo"+PHOTO_EXTENSION;
    private var FILE_PROVIDER_IDENTIFIER = "com.example.camera_integration_app";


// ===================== the code below result with an image with high quality
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var uploadImageButton : Button = findViewById(R.id.uploadImageButton);
        imageUploaded = findViewById(R.id.imageUploaded);

        uploadImageButton.setOnClickListener(View.OnClickListener {
            var goTakeAPicture : Intent  = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            absoluteLocationPathToPhoto = getPhotoFile(PHOTO_NAME);

            if(goTakeAPicture.resolveActivity(this.packageManager) != null){
               ;

                val fileProvider = FileProvider
                    .getUriForFile(this
                        ,FILE_PROVIDER_IDENTIFIER+".fileprovider",
                        absoluteLocationPathToPhoto)

                goTakeAPicture.putExtra(MediaStore.EXTRA_OUTPUT,fileProvider)

                startActivityForResult(goTakeAPicture,IMAGE_CODE_INDENTIFIER);
            } else {
                Toast
                    .makeText(this,"THERE IS NO APP THAT MATCHES THE ACTION"
                        ,Toast.LENGTH_LONG).show();
            }

        })
    }

    private fun getPhotoFile(photoName: String): File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(photoName,PHOTO_EXTENSION,storageDirectory);
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == IMAGE_CODE_INDENTIFIER && resultCode == Activity.RESULT_OK){
            val cameraResultData = BitmapFactory
                .decodeFile(absoluteLocationPathToPhoto.absolutePath)
            imageUploaded.setImageBitmap(cameraResultData);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

// ==================== the code below result with an image with low quality


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        var uploadImageButton : Button = findViewById(R.id.uploadImageButton);
//        imageUploaded = findViewById(R.id.imageUploaded);
//
//        uploadImageButton.setOnClickListener(View.OnClickListener {
//            var goTakeAPicture : Intent  = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//            if(goTakeAPicture.resolveActivity(this.packageManager) != null){
//                startActivityForResult(goTakeAPicture,IMAGE_CODE_INDENTIFIER);
//            } else {
//                Toast
//                    .makeText(this,"THERE IS NO APP THAT MATCHES THE ACTION"
//                        ,Toast.LENGTH_LONG).show();
//            }
//
//        })
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if(requestCode == IMAGE_CODE_INDENTIFIER && resultCode == Activity.RESULT_OK){
//            val cameraResultData = data?.extras?.get("data") as Bitmap;
//            imageUploaded.setImageBitmap(cameraResultData);
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }













}