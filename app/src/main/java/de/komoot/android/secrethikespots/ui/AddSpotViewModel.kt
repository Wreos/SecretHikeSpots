package de.komoot.android.secrethikespots.ui

import androidx.lifecycle.ViewModel
import com.mapbox.mapboxsdk.geometry.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddSpotViewModel: ViewModel() {
    private val _selectedLocation = MutableStateFlow<LatLng?>(null)
    val selectedLocation = _selectedLocation.asStateFlow()

    fun updateSelectedLocation(location: LatLng) {
        _selectedLocation.value = location
    }
}