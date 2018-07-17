package com.onebot.xmode

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    val TAG = "XModeTest"
    val THRESHOLD = 120;
    val TEXT_SIZE = 24F;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById(R.id.id_text_view) as TextView

        // make text bigger so it's easier to click on the 'hello'
        tv.setTextSize( TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE)

        //
        // TASK 1
        //
        tv.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(view:View, motionEvent:MotionEvent):Boolean {
                Log.d(TAG, "x: " + motionEvent.getX() + ", y: " + motionEvent.getY());
                // check if the user touched the 'hello'
                if (motionEvent.getX() < THRESHOLD) {
                    Toast.makeText(baseContext, "Task 1 Completed!", Toast.LENGTH_LONG).show();
                }
                return false
            }
        })

        //
        // TASK 2
        //
        startService(Intent(this, XModeService::class.java))


    }
}
