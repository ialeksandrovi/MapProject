package com.axwel.waytothetoilet.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.axwel.waytothetoilet.R
import com.axwel.waytothetoilet.databinding.FragmentMapBinding
import com.axwel.waytothetoilet.domain.MapsPresenter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback, MapsView {
    var map: GoogleMap? = null
    var lastKnownLocation: Location? = null
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val defaultLocation = LatLng(33.8523341, 151.2106085)
    var binding: FragmentMapBinding? = null

    @InjectPresenter
    lateinit var mapsPresenter: MapsPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMapBinding.inflate(layoutInflater)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context as Activity)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        putMarkers()
    }

    private fun putMarkers() {
        val markers = mapsPresenter.getMarkers()
        markers.forEach {
            map?.addMarker(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapsPresenter = context?.let { MapsPresenter(it.applicationContext) }!!
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getDeviceLocation()
        configureButtons()
        putMarkers()
        map?.setOnMapLongClickListener {
            addMarker(it)
        }
        map?.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }
    }

    private fun addMarker(latLng: LatLng) {
        val marker = MarkerOptions()
            .position(latLng)
            .title("Marker")
            .flat(true)
        mapsPresenter.saveMarker(marker)

        map?.addMarker(
            marker
        )

    }

    @SuppressLint("MissingPermission")
    private fun configureButtons() {
        map?.apply {
            isMyLocationEnabled = true
            uiSettings.apply {
                isCompassEnabled = true
                isZoomControlsEnabled = true
                isCompassEnabled = true
                isMyLocationButtonEnabled = true
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    lastKnownLocation = task.result
                    if (lastKnownLocation != null) {
                        map?.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude
                                ), 15F
                            )
                        )
                    }
                } else {
                    map?.moveCamera(
                        CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, 15F)
                    )
                    map?.uiSettings?.isMyLocationButtonEnabled = false
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    override fun updateMarkers(list: List<MarkerOptions>) {
        map?.clear()
        list.forEach { map?.addMarker(it) }
    }

    companion object {
        val TAG: String =
            MapFragment::class.java.canonicalName ?: MapFragment::class.java.simpleName

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

}