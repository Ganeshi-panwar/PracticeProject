package com.ganeshi.backgroundservices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings

class MyBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED){
            val isTurnedOn = Settings.Global.getInt(
                context?.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON
            ) != 0
            println("Is airplane mode enabled? $isTurnedOn")
        }
    }
}