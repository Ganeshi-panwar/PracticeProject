package com.ganeshi.broadcastreciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class MyBroadCastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
       if (Intent.ACTION_BOOT_COMPLETED == intent?.action){
           Toast.makeText(context , "Boot Completed", Toast.LENGTH_LONG).show()
                  }


        if(ConnectivityManager.CONNECTIVITY_ACTION == intent?.action) {
            val booleanExtra =
                intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
            if(!booleanExtra) {
                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show()
            }
        } else if(Intent.ACTION_TIME_TICK == intent?.action) {
            Toast.makeText(context, "Time Increment", Toast.LENGTH_LONG).show()
        }
    }
}

