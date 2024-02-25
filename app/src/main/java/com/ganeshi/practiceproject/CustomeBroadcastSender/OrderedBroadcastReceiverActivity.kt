package com.ganeshi.custombroadcastsender

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ganeshi.custombroadcastsender.databinding.ActivityOrderedBroadcastReceiverBinding

class OrderedBroadcastReceiverActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOrderedBroadcastReceiverBinding

//    private val myOrderedBroadCast = object :BroadcastReceiver(){
//        @SuppressLint("SetTextI18n")
//        override fun onReceive(context: Context?, intent: Intent?) {
//            if ("com.garishness.custombroadcastsender.ACTION_SEND" == intent?.action){
//                val intentExtra = intent.getStringExtra("com.garishness.custombroadcastsender.EXTRA_DATA")
//                binding.textView.text = ("Inner Broadcast ::: $intentExtra")
//            }
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOrderedBroadcastReceiverBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.orderedButton.setOnClickListener {
            val intent =  Intent("com.garishness.custombroadcastsender.ACTION_SEND")
            intent.setPackage("com.example.broadcastreceiver")

            val bundle = Bundle()
            bundle.putString("message_key" , "start")

            sendOrderedBroadcast(intent, null, MyBroadCastSender() , null ,
                RESULT_CANCELED, "start" , bundle)

           // sendBroadcast(intent)
        }
    }

//    override fun onStart() {
//        super.onStart()
//        val intentFilter = IntentFilter("com.garishness.custombroadcastsender.ACTION_SEND")
//        registerReceiver(myOrderedBroadCast ,intentFilter)
//    }



//    override fun onStop() {
//        super.onStop()
//        unregisterReceiver(myOrderedBroadCast)
//    }
}
