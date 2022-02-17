package com.axwel.waytothetoilet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.axwel.waytothetoilet.databinding.ActivityMapsBinding
import com.axwel.waytothetoilet.ui.MapFragment

class MapsActivity : AppCompatActivity(), AppNavigator {
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigateToMap()
    }

    override fun navigateToMap() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fContainer, MapFragment(), "MapsActivity")
            .commit()
    }

    override fun navigateToSettings() {
        TODO("Not yet implemented")
    }
}