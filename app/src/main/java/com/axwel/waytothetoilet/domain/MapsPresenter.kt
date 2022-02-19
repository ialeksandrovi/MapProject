package com.axwel.waytothetoilet.domain

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.axwel.waytothetoilet.storage.LocalMarkersStorage
import com.axwel.waytothetoilet.storage.MarkersStorage
import com.axwel.waytothetoilet.ui.MapsView
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@InjectViewState
class MapsPresenter(context: Context) : MvpPresenter<MapsView>() {
    private val markersStorage: MarkersStorage = LocalMarkersStorage(context)

    fun saveMarker(markerOptions: MarkerOptions) {
        markersStorage.addMarker(markerOptions)
    }

    fun getMarkers(): List<MarkerOptions> {
        return markersStorage.getAllMarkers()
    }

    fun removeMarker(marker: Marker) {
        val markerOptions = MarkerOptions()
            .position(marker.position)
            .title(marker.title)
        markersStorage.removeMarker(markerOptions)
    }
}