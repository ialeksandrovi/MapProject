package com.axwel.waytothetoilet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.axwel.waytothetoilet.databinding.FragmentSettingsBinding
import com.axwel.waytothetoilet.domain.SettingsPresenter
import com.google.android.gms.maps.model.MarkerOptions

class SettingsFragment : Fragment(), MarkerListView {
    private var binding: FragmentSettingsBinding? = null
    private lateinit var rvList: RecyclerView
    private lateinit var adapter: SettingsAdapter

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        presenter = SettingsPresenter(requireContext())
        adapter = SettingsAdapter()
        adapter.setListener(object : SettingsAdapter.MarkerListener {
            override fun markerUpdated(marker: MarkerOptions) {
                markerUpdate(marker)
            }

            override fun markerRemoved(marker: MarkerOptions) {
                markerRemove(marker)
            }

        })
        rvList = binding?.rvList!!
        rvList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvList.adapter = adapter
        setMarkers(presenter.getMarkers())
    }

    companion object {
        val TAG: String =
            SettingsFragment::class.java.canonicalName ?: SettingsFragment::class.java.simpleName

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun setMarkers(markers: List<MarkerOptions>) {
        adapter.setMarkers(markers)
    }

    override fun markerUpdate(marker: MarkerOptions) {
        presenter.saveMarker(marker)
        presenter.getMarkers()
    }

    override fun markerRemove(marker: MarkerOptions) {
        presenter.removeMarker(marker)
        presenter.getMarkers()
    }
}