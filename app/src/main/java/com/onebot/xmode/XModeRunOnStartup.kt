package com.onebot.xmode

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class XModeRunOnStartup: BroadcastReceiver() {
    // handle the startup intent by starting the activity
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            val i = Intent(context, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
                    or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }
}