package com.ganeshi.backgroundservices

import android.annotation.SuppressLint
import android.app.ForegroundServiceStartNotAllowedException
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.ServiceInfo
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.app.ServiceCompat.startForeground
import androidx.core.app.ServiceCompat.stopForeground
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.ganeshi.backgroundservices.MyService.Companion.ACTION_PAUSE_MUSIC
import com.ganeshi.backgroundservices.MyService.Companion.ACTION_PLAY_MUSIC
import com.ganeshi.backgroundservices.MyService.Companion.ACTION_STOP_MUSIC
import kotlinx.coroutines.NonCancellable.start

class MyService : Service() {
    private var TAG:String = "MyTag"
    private lateinit var player: MediaPlayer

    private lateinit var binder:IBinder
    private  var playBackPosition:Int = 0



    companion object {
        const val ACTION_STOP_MUSIC = "ACTION_STOP_MUSIC"
        const val ACTION_PAUSE_MUSIC = "ACTION_PAUSE_MUSIC"
        const val ACTION_PLAY_MUSIC =  "ACTION_PLAY_MUSIC"
    }

    override fun onCreate() {
        super.onCreate()
        binder = MyServiceBinder()
        initializeMediaPlayer()
        Log.d(TAG , "onCreate")
    }

    private fun initializeMediaPlayer(){
        if (!::player.isInitialized) {
            player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
            player.isLooping = true
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent?.action == ACTION_STOP_MUSIC) {
            stopMusic()
        }else if (intent?.action == ACTION_PAUSE_MUSIC){
            pause()
        }else if(intent?.action == ACTION_PLAY_MUSIC){
            start()
        }

        notificationChannel()
//
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            0,
//            Intent(this, MainActivity::class.java),
//            PendingIntent.FLAG_IMMUTABLE
//        )

        val stopIntent = Intent(this, MyService::class.java)
        stopIntent.action = ACTION_STOP_MUSIC

        val stopPendingIntent = PendingIntent.getService(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT

        )

        val pauseIntent = Intent(this , MyService::class.java)
        pauseIntent.action = ACTION_PAUSE_MUSIC
        val pausePendingIntent = PendingIntent.getService(
            this,
            0,
            pauseIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val startIntent = Intent(this , MyService::class.java)
        startIntent.action = ACTION_PLAY_MUSIC

        val startPendingIntent = PendingIntent.getService(
            this,
            0,
            startIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Build the notification
        val notification: Notification = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setContentTitle("Foreground Service")
            .setContentText("Playing music...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .addAction(R.drawable.play_icon, "Stop", stopPendingIntent)
            .addAction(R.drawable.play_icon , "Pause", pausePendingIntent)
            .addAction(R.drawable.playpause_icon, "Start" , startPendingIntent)
           // .setContentIntent(pendingIntent)
            .build()
        startForeground(1 , notification)



        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        if (::player.isInitialized && player.isPlaying) {
            player.stop()
        }
        super.onDestroy()
    }

    private fun stopMusic() {
        if (::player.isInitialized && player.isPlaying) {
            player.stop()
            player.reset()
            player.release()
            stopForeground(true)
            stopSelf()
        }
    }

    private  fun pause(){
        if (::player.isInitialized && player.isPlaying){
            playBackPosition = player.currentPosition
            player.pause()
        }
    }
    private  fun start(){
        if (::player.isInitialized && !player.isPlaying){
            player.seekTo(playBackPosition)
            player.start()

        }
    }

    private fun notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "CHANNEL_ID",
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    public  class MyServiceBinder: Binder(){
        fun  getService():MyService{
            return MyService()
        }
    }


    override fun onBind(intent: Intent): IBinder {
        return binder
    }



}

