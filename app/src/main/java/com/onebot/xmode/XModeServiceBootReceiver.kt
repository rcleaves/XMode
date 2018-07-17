package com.onebot.xmode

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class XModeServiceBootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        {
            val serviceIntent = Intent(context, XModeService::class.java)
            context.startService(serviceIntent)
        }
    }
}