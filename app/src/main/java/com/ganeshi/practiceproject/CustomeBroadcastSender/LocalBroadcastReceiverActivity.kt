package com.ganeshi.custombroadcastsender

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ganeshi.custombroadcastsender.databinding.ActivityLocalBroadcastReceiverBinding

class LocalBroadcastReceiverActivity : AppCompatActivity() {


    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val data = intent?.getStringExtra("key")
            binding.textView1.text = data
            Toast.makeText(this@LocalBroadcastReceiverActivity, "Local Broadcast!!!", Toast.LENGTH_LONG).show()
        }
    }

    private lateinit var binding: ActivityLocalBroadcastReceiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalBroadcastReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val serviceIntent = Intent(this, MyIntentService::class.java)
            serviceIntent.putExtra("key", "initial value")
            startService(serviceIntent)
        }
    }
    override fun onStart(){
        super.onStart()
        val intentFilter = IntentFilter(MyIntentService.MY_SERVICE_INTENT)
        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(applicationContext)
            .unregisterReceiver(broadcastReceiver)
    }
}