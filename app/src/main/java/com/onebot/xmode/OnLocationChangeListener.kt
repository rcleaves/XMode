package com.onebot.xmode

import android.location.Location

interface OnLocationChangedListener {
    fun onLocationChanged(currentLocation: Location)
}