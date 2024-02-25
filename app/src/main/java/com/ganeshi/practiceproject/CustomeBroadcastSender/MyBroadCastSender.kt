package com.ganeshi.custombroadcastsender

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class MyBroadCastSender: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        // implicit intent connection wifi data etc.
        //provides information about the device's network connectivity.
        if (ConnectivityManager.EXTRA_NO_CONNECTIVITY == intent?.action){
            val booleanExtra = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false)
            if (!booleanExtra){
                Toast.makeText(context , "Connected" ,Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "Disconnected"  , Toast.LENGTH_LONG).show()
            }

             // implicit intent for time
        }else if(Intent.ACTION_TIME_TICK == intent?.action){
            Toast.makeText(context , "Time Increment" , Toast.LENGTH_LONG).show()
        }

        // explicit intent
        Toast.makeText(context , "My Broadcast Called" , Toast.LENGTH_LONG).show()
   }
}