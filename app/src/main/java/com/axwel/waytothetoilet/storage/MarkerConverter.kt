package com.axwel.waytothetoilet.storage

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject
import java.lang.StringBuilder

class MarkerConverter : Converter {
    override fun toMarkerOptions(string: String): MarkerOptions {
        val markerOptions = MarkerOptions()
        var string = string
        val latitude: Double =
            string.substring(string.indexOf("latitude:") + 9, string.indexOf(";")).toDouble()
        string = string.substring(string.indexOf(";") + 1)
        val longitude: Double =
            string.substring(string.indexOf("longitude:") + 10, string.indexOf(";")).toDouble()
        val name: String = string.substring(string.indexOf("name:") + 5)
        val latLng = LatLng(latitude, longitude)
        return markerOptions.position(latLng).title(name)
    }

    override fun fromMarkerOptions(markerOptions: MarkerOptions): String {
        val string: StringBuilder = StringBuilder().apply {
            append("latitude:${markerOptions.position.latitude};")
            append("longitude:${markerOptions.position.longitude};")
            append("name:${markerOptions.title}")
        }
        return string.toString()
    }

}

interface Converter {
    fun toMarkerOptions(string: String): MarkerOptions
    fun fromMarkerOptions(markerOptions: MarkerOptions): String
}