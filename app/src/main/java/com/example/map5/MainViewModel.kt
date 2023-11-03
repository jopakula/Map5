package com.example.map5

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.map5.data.HttpApi
import com.example.map5.data.models.PointModel
import com.example.map5.screens.MapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val DEFAULT_ZOOM = 14F

class MainViewModel : ViewModel() {
    private val mapFragment = MapFragment()

    val points = MutableLiveData(listOf<PointModel>())
    var map: GoogleMap? = null
    val bottomSheetState = MutableLiveData(BottomSheetBehavior.STATE_COLLAPSED)

    suspend fun loadPoints() {
        HttpApi.getPoints().let { newPoints ->
            withContext(Dispatchers.Main) {
                points.value = newPoints
            }
        }
    }

    fun openMap(activity: FragmentActivity) {
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragmentContainer, mapFragment)
            .commit()
    }

    fun moveCameraToPoint(point: PointModel) {
        bottomSheetState.value = BottomSheetBehavior.STATE_COLLAPSED
        map?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(point.latitude, point.longitude),
                DEFAULT_ZOOM,
            ),
        )
    }

    fun addMarkerPoints() {
        map?.clear()
        points.value?.forEach { point ->
            map?.addMarker(
                MarkerOptions()
                    .title("ID: ${point.id}, Temp: ${point.temp}")
                    .position(LatLng(point.latitude, point.longitude)),
            )
        }
    }
}