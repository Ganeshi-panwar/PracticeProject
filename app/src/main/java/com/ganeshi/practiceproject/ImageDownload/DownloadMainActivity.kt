package com.ganeshi.practiceproject.ImageDownload

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.ganeshi.practiceproject.R
import java.io.File

class DownloadMainActivity : AppCompatActivity() {
    private lateinit var downloadImage:Button
    private lateinit var enterLink:EditText
    private var permission = 0
    // registerForActivityResult  function it handle the activity result  handle permission request
    private  var requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        permission = if (it) {
            1
        } else {
            0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.download_activity_main)
        downloadImage = findViewById(R.id.btDownloadImage)
        enterLink = findViewById(R.id.etLink)

        downloadImage.setOnClickListener {
            requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if(permission==1){
                download(enterLink.text.toString(),"Noob Developer")
            }
            else{
                Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun download(urll:String, fileName:String){
        try {
            val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            //The Uri.parse method is used to create a Uri object from the given string urll.
            // In Android, Uri is a class that represents a Uniform Resource Identifier,
            // which could be a URL, a file path, or any other type of identifier.
            val imageLink = Uri.parse(urll)
            val request = DownloadManager.Request(imageLink)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                .setMimeType("image/jpeg")
                //.setAllowedOverRoaming(false)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
               // .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setTitle(fileName)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, File.separator+fileName+".jpg")
            downloadManager.enqueue(request)
            Toast.makeText(this, "Image is Downloaded" , Toast.LENGTH_LONG).show()
        }
        catch (e:Exception){
            Toast.makeText(this,"Image Download Failed", Toast.LENGTH_LONG).show()

        }
    }
}