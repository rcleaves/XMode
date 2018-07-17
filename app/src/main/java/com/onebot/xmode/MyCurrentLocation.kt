package com.onebot.xmode

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
//import com.google.android.gms.maps.LocationSource

public class MyCurrentLocation(onLocationChangedListener:OnLocationChangedListener):GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mLastLocation: Location
    private lateinit var mLocationRequest: LocationRequest
    private var onLocationChangedListener: OnLocationChangedListener

    init{
        this.onLocationChangedListener = onLocationChangedListener
    }

    @Synchronized public fun buildGoogleApiClient(context: Context) {
        mGoogleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(10 * 1000) // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000) // 1 second, in milliseconds
    }

    fun start() {
        mGoogleApiClient.connect()
    }

    fun stop() {
        mGoogleApiClient.disconnect()
    }

    override fun onConnected(bundle: Bundle?) {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient)
        if (mLastLocation != null)
        {
            onLocationChangedListener.onLocationChanged(mLastLocation)
        }
    }

    override fun onConnectionSuspended(i:Int) {
    }

    override fun onConnectionFailed(connectionResult:ConnectionResult) {
        Log.e("XMODE", "Location services connection failed with code " + connectionResult.getErrorCode())
    }

    override fun onLocationChanged(location:Location) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient)
        if (mLastLocation != null)
        {
            onLocationChangedListener.onLocationChanged(mLastLocation)
        }
    }
}