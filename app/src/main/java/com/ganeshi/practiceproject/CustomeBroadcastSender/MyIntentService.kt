package com.ganeshi.custombroadcastsender

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlin.concurrent.thread

class MyIntentService : IntentService("MyIntentService") {

    companion object {
        const val MY_SERVICE_INTENT = "MY_SERVICE_INTENT"
    }

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        var data = intent?.getStringExtra("key")
        try {
            Thread.sleep(4000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val resultIntent = Intent(MY_SERVICE_INTENT)
        resultIntent.putExtra("key", "Download has been completed")
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(resultIntent)
    }

}