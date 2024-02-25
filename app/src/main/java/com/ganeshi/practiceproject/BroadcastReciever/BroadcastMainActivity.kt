package com.ganeshi.practiceproject.BroadcastReciever

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ganeshi.broadcastreciver.MyBroadCastReceiver
import com.ganeshi.practiceproject.databinding.BroadcastActivityMainBinding

class BroadcastMainActivity : AppCompatActivity() {
    private lateinit var myBroadCastReceiver: MyBroadCastReceiver
    private lateinit var binding: BroadcastActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = BroadcastActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        myBroadCastReceiver = MyBroadCastReceiver()
        val intent = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(myBroadCastReceiver, intent)

        val timeIntent = IntentFilter(Intent.ACTION_TIME_TICK)
        registerReceiver(myBroadCastReceiver , timeIntent)
    }

//    public fun sendBroadcast() {
//        val intent = Intent( "com.garishness.broadcast_receiver.ACTION_SEND")
//        intent.putExtra("com.garishness.EXTRA_DATA" , "Hello from sender app!")
//        sendBroadcast(intent)
//    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myBroadCastReceiver)
    }
}
