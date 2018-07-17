package com.onebot.xmode

import android.app.Service
import android.content.Intent
import android.os.Handler;
import android.os.IBinder
import android.widget.Toast
import com.onebot.xmode.MainActivity.Companion.myLocation
import java.util.*
import java.text.SimpleDateFormat;

// simple service to execute task periodically
class XModeService: Service() {

    // run on another Thread to avoid crash
    private val mHandler = Handler()

    // timer handling
    private var mTimer: Timer?  = null

    // needed for superclass
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    // setup timer as local variable and initialize if necessary
    override fun onCreate() {
        var mt = mTimer;
        // cancel if already existed
        if (mt != null)
        {
            mt.cancel()
        }
        else
        {
            // recreate new
            mt = Timer()
        }
        // schedule task
        mt.scheduleAtFixedRate(TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL)

        if(BuildConfig.FLAVOR.equals("debugLocation")) {
            // debug variant
            NUM_SECS = 20;
        } else {
            // release variant
            NUM_SECS = 60*60;
        }

    }

    // keep service running even when in background
    override fun onStartCommand(intent:Intent, flags:Int, startId:Int):Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    // runnable task for executing
    internal inner class TimeDisplayTimerTask:TimerTask() {
        private// get date time in custom format
        val dateTime:String

            get() {
                val sdf = SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]")
                return sdf.format(Date())
            }

        // run on another thread
        override fun run() {
            mHandler.post(object:Runnable {
                public override fun run() {
                    // display toast
                        Toast.makeText(getApplicationContext(), "LOCATION: " + MainActivity.myLocation?.toString(),
                                Toast.LENGTH_LONG).show()
                    }

                // TODO - put data into 1) google cloud database (noSQL)
                // 2) SQLite on device (not recommeneded if you need to process the data on back-end)


            })
        }
    }

    // inner class attributes
    companion object {
        //private val NUM_SECS = 60*60;
        // debug
        var NUM_SECS = 60;
        // constant
        var NOTIFY_INTERVAL = (NUM_SECS * 1000).toLong() // ms
    }
}