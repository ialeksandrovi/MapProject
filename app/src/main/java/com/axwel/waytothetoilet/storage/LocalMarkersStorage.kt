package com.axwel.waytothetoilet.storage

import android.content.Context
import com.google.android.gms.maps.model.MarkerOptions

class LocalMarkersStorage(context: Context) : MarkersStorage {
    private val prefsMarkers = context.getSharedPreferences(ARG_MARKERS, Context.MODE_PRIVATE)
    private val prefsMarkerIds = context.getSharedPreferences(ARG_MARKER_IDS, Context.MODE_PRIVATE)
    private val markerConverter = MarkerConverter()


    companion object {
        private const val ARG_MARKERS = "ARG_MARKERS"
        private const val ARG_MARKER_IDS = "ARG_MARKER_IDS"
    }

    override fun addMarker(markerOptions: MarkerOptions) {
        val map = prefsMarkers.all as Map<String, String>
        if (map.values.any { markerConverter.toMarkerOptions(it).position == markerOptions.position }) {
            saveToPrefs(
                getMarkerKey(markerConverter.fromMarkerOptions(markerOptions)),
                markerConverter.fromMarkerOptions(markerOptions)
            )
        } else {
            var firstAvailableKey = 0
            while (!map.containsKey(firstAvailableKey.toString())) {
                firstAvailableKey++
            }
            saveToPrefs(
                firstAvailableKey.toString(),
                markerConverter.fromMarkerOptions(markerOptions)
            )
        }
    }

    private fun saveToPrefs(key: String, value: String) {
        prefsMarkers.edit().putString(key, value).apply()
    }


    override fun removeMarker(markerOptions: MarkerOptions) {
        val map = prefsMarkers.all
        if (map.containsValue(markerConverter.fromMarkerOptions(markerOptions))) {
            map.remove(getMarkerKey(markerConverter.fromMarkerOptions(markerOptions)))
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

    private fun getAllIds(): List<Int> {
        val ids: MutableList<Int> = mutableListOf()
        prefsMarkerIds.all.keys.forEach {
            ids.add(it.toInt())
        }
        return ids
    }
}