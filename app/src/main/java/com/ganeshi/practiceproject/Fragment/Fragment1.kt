package com.ganeshi.fragment

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.ganeshi.fragment.databinding.Fragment1Binding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileOutputStream


class Fragment1 : Fragment() {
    private lateinit var communicator: Communicator
    private lateinit var binding: Fragment1Binding
    private lateinit var imageView: ImageView
    private lateinit var button: FloatingActionButton
    private var selectImageURL:Uri? = null
    private var downloadId: Long = 0

private var contract = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
    // it represent the result which is the content URI of the selected image.
    selectImageURL = uri
    imageView.setImageURI(uri)
}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = Fragment1Binding.inflate(inflater, container, false)
        communicator = activity as Communicator
        button = binding.floatingActionButton
        imageView = binding.image

       button.setOnClickListener {
            pickImageGallery()
        }
        
        
        binding.downlodeButton.setOnClickListener{
           // downloadImage()
        }


      binding.sentButtonClick.setOnClickListener {
            communicator.passDataCom(binding.messageInput.text.toString() , binding.addressText.text.toString())
        }

        return binding.root
    }

    private fun pickImageGallery() {

        contract.launch("image/*")

    }



}





