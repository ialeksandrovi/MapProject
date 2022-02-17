package com.axwel.waytothetoilet.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.axwel.waytothetoilet.databinding.ItemMarkerBinding
import com.google.android.gms.maps.model.MarkerOptions

class SettingsAdapter : RecyclerView.Adapter<SettingsAdapter.MarkerViewHolder>() {
    private var binding: ItemMarkerBinding? = null

    private var markers: List<MarkerOptions> = mutableListOf()
    private var listener: MarkerListener? = null

    fun setMarkers(markers: List<MarkerOptions>) {
        this.markers = markers
    }

    fun setListener(listener: MarkerListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerViewHolder {
        binding = ItemMarkerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarkerViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: MarkerViewHolder, position: Int) {
        val marker = markers[position]
        holder.bind(marker)
    }

    override fun getItemCount(): Int {
        return markers.size
    }


    inner class MarkerViewHolder(private var itemMarkerBinding: ItemMarkerBinding) :
        RecyclerView.ViewHolder(itemMarkerBinding.root) {
        fun bind(marker: MarkerOptions) {
            itemMarkerBinding.apply {
                tvName.setText(marker.title)
                tvPosition.text = "${marker.position.latitude} : ${marker.position.longitude}"

                tvName.addTextChangedListener {
                    listener?.markerUpdated(marker)
                }
            }
        }
    }

    interface MarkerListener {
        fun markerUpdated(marker: MarkerOptions)
    }


}