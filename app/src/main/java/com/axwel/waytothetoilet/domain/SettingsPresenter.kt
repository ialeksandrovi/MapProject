package com.axwel.waytothetoilet.domain

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.axwel.waytothetoilet.storage.LocalMarkersStorage
import com.axwel.waytothetoilet.storage.MarkersStorage
import com.axwel.waytothetoilet.ui.MarkerListView
import com.google.android.gms.maps.model.MarkerOptions

@InjectViewState
class SettingsPresenter(context: Context) : MvpPresenter<MarkerListView>() {
    private val markersStorage: MarkersStorage = LocalMarkersStorage(context)

    fun getMarkers(): List<MarkerOptions> {
        return markersStorage.getAllMarkers()
    }

    fun saveMarker(markerOptions: MarkerOptions) {
        markersStorage.addMarker(markerOptions)
    }

    fun removeMarker(markerOptions: MarkerOptions) {
        markersStorage.removeMarker(markerOptions)
    }
}