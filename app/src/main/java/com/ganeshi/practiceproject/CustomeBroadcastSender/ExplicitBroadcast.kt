package com.ganeshi.custombroadcastsender

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ExplicitBroadcast:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
       Toast.makeText(context , "ExplicitBroadcast receiver" , Toast.LENGTH_LONG).show()
    }
}