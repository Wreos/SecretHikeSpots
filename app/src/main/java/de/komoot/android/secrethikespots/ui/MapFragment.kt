package de.komoot.android.secrethikespots.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.innfinity.permissionflow.lib.requestPermissions
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.SupportMapFragment
import de.komoot.android.secrethikespots.R
import kotlinx.coroutines.flow.MutableStateFlow

class MapFragment : Fragment() {

    companion object {
        const val MAP_STYLE = "https://tiles-api.maps.komoot.net/v1/style.json?optimize=true&clustered=true"
    }

    private val viewModel: AddSpotViewModel by lazy {
        ViewModelProvider(requireActivity())[AddSpotViewModel::class.java]
    }
    private val locationAllowed = MutableStateFlow(false)
    private var cameraMoved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            requestPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ).collect { permissions ->
                permissions.firstOrNull { it.isGranted }?.let {
                    locationAllowed.value = true
                } ?: run {
                    locationAllowed.value = false
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { map ->
            map.setStyle(MAP_STYLE)

            map.addOnMapClickListener {
                viewModel.updateSelectedLocation(it)
                true
            }

            map.addOnCameraMoveStartedListener {
                if (it == MapboxMap.OnCameraMoveStartedListener.REASON_API_GESTURE)
                    cameraMoved = true
            }

            lifecycleScope.launchWhenStarted {
                locationAllowed.collect {
                    if (it && hasLocationPermission()) {
                        map.getStyle {
                            activateLocationComponent(map)
                            if (!cameraMoved)
                                centerOnLocation(map)
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun activateLocationComponent(map: MapboxMap) {
            val options =
                LocationComponentActivationOptions.builder(requireContext(), map.style!!)
                    .build()
            map.locationComponent.activateLocationComponent(options)
            map.locationComponent.isLocationComponentEnabled = true
    }

    @SuppressLint("MissingPermission")
    private fun centerOnLocation(map: MapboxMap) {

        fun center(location: Location) {
            val latLng = LatLng(location)
            val zoom = when(location.accuracy) {
                in 0f..25f -> 19.0
                in 26f..50f -> 18.0
                in 51f..75f -> 17.0
                in 76f..100f -> 16.0
                else -> 14.0
            }
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
        }

        val request = LocationRequest.create()
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val callback = object: LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                p0.lastLocation?.let {
                    center(it)
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(request, callback, Looper.getMainLooper())
    }

    private fun hasLocationPermission() : Boolean {

        fun check(permission: String) =
            ActivityCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED

        return check(Manifest.permission.ACCESS_FINE_LOCATION) || check(Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}