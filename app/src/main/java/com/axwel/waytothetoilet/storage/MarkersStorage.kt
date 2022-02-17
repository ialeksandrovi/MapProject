package com.axwel.waytothetoilet.storage

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

interface MarkersStorage {
    fun addMarker(markerOptions: MarkerOptions)
    fun removeMarker(markerOptions: MarkerOptions)
    fun getAllMarkers(): List<MarkerOptions>
}