package com.awesomejeqmaps

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.google.android.gms.maps.MapsInitializer

class AwesomeJeqMapsModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "AwesomeJeqMaps"
    }

    @ReactMethod
    fun initializeMap(promise: Promise) {
        try {
            MapsInitializer.initialize(reactApplicationContext)
            promise.resolve("Map initialized successfully")
        } catch (e: Exception) {
            promise.reject("INITIALIZATION_FAILED", "Failed to initialize Maps: ${e.message}")
        }
    }
}
