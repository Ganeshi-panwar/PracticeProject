package com.ganeshi.backgroundservices

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.ganeshi.practiceproject.databinding.ServiceActivityMainBinding

class ServiceMainActivity : AppCompatActivity() {


    private  val airplaneModeReceiver = MyBroadcastReceiver()
    private val TAG:String = "MyTag"
    private lateinit var binding:ServiceActivityMainBinding
    private lateinit var mService: MyService
    private var mBound:Boolean = false





    private val connection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyService.MyServiceBinder
            mService = binder.getService()
            mBound = true
            Log.d(TAG, "onServiceConnected")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
            Log.d(TAG, "onServiceDisconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ServiceActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate")

//
//        MyBroadcastReceiver(savedInstanceState)
        registerReceiver(
            airplaneModeReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )

        binding.apply {
            statbtn.setOnClickListener {
                if (mBound){
                    sendBroadcast(
                        Intent("TEXT_ACTION")
                    )
                    val playIntent = Intent(this@ServiceMainActivity , MyService::class.java)
                    playIntent.action = MyService.ACTION_PLAY_MUSIC
                    startService(playIntent)
                }
            }
            stopbtn.setOnClickListener {
                if (mBound){
                    val stopIntent = Intent(this@ServiceMainActivity , MyService::class.java)
                    stopIntent.action = MyService.ACTION_STOP_MUSIC
                    startService(stopIntent)

                }
                // stopService(Intent(this@MainActivity , MyService::class.java))
            }
            pausebtn.setOnClickListener {
                if (mBound) {
                    val pauseIntent = Intent(this@ServiceMainActivity, MyService::class.java)
                    pauseIntent.action = MyService.ACTION_PAUSE_MUSIC
                    startService(pauseIntent)
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        Intent(this , MyService::class.java).also { intent ->
            bindService(intent , connection , Context.BIND_AUTO_CREATE)
            Log.d(TAG,"onStart")
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneModeReceiver)
    }
}
