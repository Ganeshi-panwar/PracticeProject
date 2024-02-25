package com.ganeshi.custombroadcastsender

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ganeshi.custombroadcastsender.databinding.BroadcastActivityMainBinding

class BroadcastMainActivity : AppCompatActivity() {

    private var myBroadCastSender = MyBroadCastSender()
    private lateinit var binding: BroadcastActivityMainBinding


    private val broad = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if ("com.garishness.custombroadcastsender.ACTION_SEND" == intent?.action) {
                val intentExtra = intent.getStringExtra("com.garishness.custombroadcastsender.EXTRA_DATA")
                "Inner Broadcast received: $intentExtra".also { binding.textView.text = it }
                //    binding.textView.text = ("Inner Broadcast received:   $intentExtra")
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BroadcastActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        intentFilter.addAction(Intent.ACTION_TIME_TICK)
        registerReceiver(myBroadCastSender , intentFilter)


        binding.sendButton.setOnClickListener {
            val intent = Intent("com.garishness.custombroadcastsender.ACTION_SEND")
            intent.putExtra("com.garishness.custombroadcastsender.EXTRA_DATA", "Hello From Sender app..")
            sendBroadcast(intent)

            // explicit intent
            val intent1 = Intent(this , myBroadCastSender.javaClass)
            sendBroadcast(intent1)
        }
    }
    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter("com.garishness.custombroadcastsender.ACTION_SEND")
        registerReceiver(broad, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broad)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myBroadCastSender)
    }
}