package com.axwel.waytothetoilet.ui

import com.arellomobile.mvp.MvpView
import com.google.android.gms.maps.model.MarkerOptions

interface MarkerListView : MvpView {
    fun setMarkers(markers: List<MarkerOptions>)
    fun markerUpdate(marker: MarkerOptions)
    fun markerRemove(marker: MarkerOptions)
}