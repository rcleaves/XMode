package com.onebot.xmode

import android.Manifest
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), OnLocationChangedListener {

    val TAG = "XModeTest"
    val THRESHOLD = 120;
    val TEXT_SIZE = 24F;

    // singleton placeholder
    companion object {
        var myLocation: Location = Location("")
    }

    private lateinit var myCurrentLocation: MyCurrentLocation

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

        checkPermission()

        //
        // TASK 2
        //
        startService(Intent(this, XModeService::class.java))


    }

    override fun onLocationChanged(location:Location) {
        //Toast.makeText(this, "location: " + location, Toast.LENGTH_SHORT).show();
        myLocation = location
    }

    val MY_PERMISSIONS_REQUEST_CODE = 123

    fun checkPermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        !== PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        !== PackageManager.PERMISSION_GRANTED))
        {
            // permission not granted, have to request
            ActivityCompat.requestPermissions(this,
                    arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    MY_PERMISSIONS_REQUEST_CODE)
        } else {
            myCurrentLocation = MyCurrentLocation(this)
            myCurrentLocation.buildGoogleApiClient(this)
            myCurrentLocation.start()
        }
    }

    // if we get the user's permission, start the location service
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the 3rd task
                    myCurrentLocation = MyCurrentLocation(this)
                    myCurrentLocation.buildGoogleApiClient(this)
                    myCurrentLocation.start()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Cannot get current location", Toast.LENGTH_LONG).show()
                }
                return
            }

        // Add other 'when' lines to check for other
        // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}

