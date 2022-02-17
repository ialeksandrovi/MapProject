package com.axwel.waytothetoilet.ui

import android.view.View
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import moxy.MvpView

interface MapsView : MvpView {
    fun updateMarkers(list: List<MarkerOptions>)
}