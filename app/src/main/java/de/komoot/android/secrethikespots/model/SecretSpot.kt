package de.komoot.android.secrethikespots.model

import android.os.Parcelable
import com.mapbox.mapboxsdk.geometry.LatLng
import de.komoot.android.secrethikespots.data.SecretSpotEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class SecretSpot(
    internal val id: Long,
    val name: String,
    val location: LatLng,
    val image: String
) : Parcelable


data class NewSpot(val name: String, val location: LatLng, val image: String)
data class UpdateSpot(val spot: SecretSpot, val name: String)

fun SecretSpotEntity.toModel() = SecretSpot(id, name, LatLng(lat, lon), image)