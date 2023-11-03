package com.example.map5.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.fragment.app.activityViewModels
import com.example.map5.MainViewModel
import com.example.map5.R
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback

class MapFragment : Fragment() {

    private val homeFragment = ListFragment()

    private val mainViewModel: MainViewModel by activityViewModels()
    private var isFirstInit = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val v = inflater.inflate(R.layout.fragment_map, container, false)
        val googleMap = childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
        val bottomSheetContainer = v.findViewById<FrameLayout>(R.id.bottomSheetContainer)
        val bottomSheetBehaviour = BottomSheetBehavior.from(bottomSheetContainer)

        googleMap.getMapAsync { map ->
            mainViewModel.map = map
            mainViewModel.points.observe(viewLifecycleOwner) { points ->
                mainViewModel.addMarkerPoints()

                if (isFirstInit) {
                    points.getOrNull(0)?.let { point ->
                        mainViewModel.moveCameraToPoint(point)
                        isFirstInit = false
                    }
                }
            }
        }

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.bottomSheetContainer, homeFragment)
            ?.commit()

        bottomSheetBehaviour.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        mainViewModel.bottomSheetState.observe(viewLifecycleOwner) { state ->
            bottomSheetBehaviour.state = state
        }

        return v
    }
}