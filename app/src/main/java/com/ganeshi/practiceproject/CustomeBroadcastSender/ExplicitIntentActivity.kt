package com.ganeshi.custombroadcastsender

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ganeshi.custombroadcastsender.databinding.ActivityExplixcitIntentBinding

class ExplicitIntentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityExplixcitIntentBinding
    private val myExplicitBroadcastReceiver = ExplicitBroadcast()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExplixcitIntentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


         binding.button.setOnClickListener {
            // val intent = Intent( this, myExplicitBroadcastReceiver.javaClass)
//             val component = ComponentName(this , myExplicitBroadcastReceiver.javaClass)
             val component = ComponentName("com.example.broadcastreceiver" , "com.example.broadcastreceiver.MyBroadCastReceiver")
             intent.setComponent(component)
             sendBroadcast(intent)
         }
    }
}