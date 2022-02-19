package com.axwel.waytothetoilet.storage

import android.content.Context
import com.google.android.gms.maps.model.MarkerOptions

class LocalMarkersStorage(context: Context) : MarkersStorage {
    private val prefsMarkers = context.getSharedPreferences(ARG_MARKERS, Context.MODE_PRIVATE)
    private val markerConverter = MarkerConverter()


    companion object {
        private const val ARG_MARKERS = "ARG_MARKERS"
    }

    override fun addMarker(markerOptions: MarkerOptions) {
        val map = prefsMarkers.all
        if (map.isNotEmpty()) {
            if (map.values.any { markerConverter.toMarkerOptions(it as String).position == markerOptions.position }) {
                saveToPrefs(
                    getMarkerKey(markerConverter.fromMarkerOptions(markerOptions)),
                    markerConverter.fromMarkerOptions(markerOptions)
                )
            } else {
                var firstAvailableKey = 0
                while (map.containsKey(firstAvailableKey.toString())) {
                    firstAvailableKey++
                }
                saveToPrefs(
                    firstAvailableKey.toString(),
                    markerConverter.fromMarkerOptions(markerOptions)
                )
            }
        } else {
            saveToPrefs(
                0.toString(),
                markerConverter.fromMarkerOptions(markerOptions)
            )
        }

    }

    private fun saveToPrefs(key: String, value: String) {
        prefsMarkers.edit().putString(key, value).apply()
    }


    override fun removeMarker(markerOptions: MarkerOptions) {
        val map = prefsMarkers.all
        var key = ""
        if (map.containsValue(markerConverter.fromMarkerOptions(markerOptions))) {
            map.entries.forEach {
                if (it.value == markerConverter.fromMarkerOptions(markerOptions)) {
                    key = it.key
                }
            }
            prefsMarkers.edit().remove(key).apply()
        }

    }

    private fun getMarkerKey(string: String): String {
        val originMarkerPosition = markerConverter.toMarkerOptions(string).position
        val map = prefsMarkers.all
        val markerKeys = map.keys
        var markerKey = 0
        markerKeys.forEach {
            val position = markerConverter.toMarkerOptions(map[it] as String).position
            if (originMarkerPosition == position) {
                markerKey = it.toInt()
            }
        }
        return markerKey.toString()
    }

    override fun getAllMarkers(): List<MarkerOptions> {
        val markers: MutableList<MarkerOptions> = mutableListOf()
        prefsMarkers.all.values.forEach {
            markers.add(markerConverter.toMarkerOptions(it as String))
        }
        return markers
    }
}