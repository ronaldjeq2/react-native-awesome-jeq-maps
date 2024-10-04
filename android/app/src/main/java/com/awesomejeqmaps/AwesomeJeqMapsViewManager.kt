package com.awesomejeqmaps

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.uimanager.events.RCTEventEmitter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class AwesomeJeqMapsViewManager : SimpleViewManager<MapView>() {
    private var googleMap: GoogleMap? = null
    private lateinit var reactContext: ThemedReactContext

    override fun getName(): String {
        return "AwesomeJeqMapsView"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): MapView {
        this.reactContext = reactContext
        val mapView = MapView(reactContext)
        mapView.onCreate(null)
        mapView.getMapAsync { googleMap ->
            this.googleMap = googleMap

            googleMap?.let { map ->
                map.setOnMarkerClickListener { marker ->
                    marker.showInfoWindow()
                    val zoomLevel = map.cameraPosition.zoom + 2
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(marker.position, zoomLevel)
                    map.animateCamera(cameraUpdate)

                    val event: WritableMap = Arguments.createMap().apply {
                        putDouble("latitude", marker.position.latitude)
                        putDouble("longitude", marker.position.longitude)
                        putString("title", marker.title)
                    }
                    reactContext.getJSModule(RCTEventEmitter::class.java)
                        .receiveEvent(mapView.id, "topMarkerClick", event)
                    true
                }

                addMarkersToMap(map, pendingMarkerData)
            }
        }
        return mapView
    }

    private var pendingMarkerData: ReadableArray? = null

    @ReactProp(name = "markerData")
    fun setMarkerData(view: MapView, markerData: ReadableArray?) {
        if (googleMap != null) {
            addMarkersToMap(googleMap!!, markerData)
        } else {
            pendingMarkerData = markerData
        }
    }

    private fun addMarkersToMap(map: GoogleMap, markerData: ReadableArray?) {
        if (markerData == null) return
        map.clear()

        if (markerData.size() == 0) return

        val boundsBuilder = LatLngBounds.Builder()

        for (i in 0 until markerData.size()) {
            val marker = markerData.getMap(i)
            val position = LatLng(marker?.getDouble("latitude") ?: 0.0, marker?.getDouble("longitude") ?: 0.0)
            val title = marker?.getString("title") ?: ""

            map.addMarker(MarkerOptions().position(position).title(title))

            boundsBuilder.include(position)
        }

        if (markerData.size() > 0) {
            val bounds = boundsBuilder.build()
            val padding = 100
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
        }
    }

    override fun getExportedCustomDirectEventTypeConstants(): Map<String, Any>? {
        return mapOf(
            "topMarkerClick" to mapOf("registrationName" to "onMarkerClick")
        )
    }
}
