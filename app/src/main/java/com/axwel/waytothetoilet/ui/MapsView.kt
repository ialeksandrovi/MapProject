package com.axwel.waytothetoilet.ui

import com.arellomobile.mvp.MvpView
import com.google.android.gms.maps.model.MarkerOptions

interface MapsView : MvpView {
    fun updateMarkers(list: List<MarkerOptions>)
}