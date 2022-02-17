package com.axwel.waytothetoilet.storage

import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject

class MarkerConverter : Converter {
    override fun toMarkerOptions(string: String): MarkerOptions {
        val json = JSONObject(string)
        return json["Marker"] as MarkerOptions
    }

    override fun fromMarkerOptions(markerOptions: MarkerOptions): String {
        val json: JSONObject = JSONObject()
        json.put("Marker", markerOptions)
        return json.toString()
    }

}

interface Converter {
    fun toMarkerOptions(string: String): MarkerOptions
    fun fromMarkerOptions(markerOptions: MarkerOptions): String
}